package com.example.bluetoothdevicediscover.app.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.bluetoothdevicediscover.app.presenter.RandomUserPresenter
import com.example.bluetoothdevicediscover.app.viewAction.BaseViewAction
import com.example.bluetoothdevicediscover.app.viewAction.RandomUserViewAction
import com.example.bluetoothdevicediscover.app.viewState.RandomUserViewState
import com.example.bluetoothdevicediscover.domain.entity.Location
import com.example.bluetoothdevicediscover.domain.entity.PhoneInfo
import com.example.bluetoothdevicediscover.domain.entity.UpdateAppInfoResult
import com.example.bluetoothdevicediscover.domain.entity.User
import com.example.bluetoothdevicediscover.domain.manager.InformationUpdater
import com.example.bluetoothdevicediscover.domain.entity.Tuple3
import com.example.bluetoothdevicediscover.domain.repository.UserLocationRepository
import com.example.bluetoothdevicediscover.domain.repository.UserPhoneRepository
import com.example.bluetoothdevicediscover.domain.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomUserViewModel @Inject constructor(
    application: Application,
    private val informationUpdater: InformationUpdater,
    private val userRepository: UserRepository,
    private val userPhoneRepository: UserPhoneRepository,
    private val userLocationRepository: UserLocationRepository
): BaseViewModel<RandomUserViewState, RandomUserViewAction>(application) {

    private var firstFetchedUserId: String? = null
    private var currentUserData: Tuple3<User?, Location?, List<PhoneInfo>?>? = null

    override fun determineFreshViewState(): RandomUserViewState {
        val immutableCurrentUserData = currentUserData ?: return RandomUserViewState.EmptyState
        return RandomUserPresenter.generateUserDetailsViewState(
            immutableCurrentUserData.o1,
            immutableCurrentUserData.o3,
            immutableCurrentUserData.o2, firstFetchedUserId)
    }

    private suspend fun handleUpdateAppInfoResultOk(freshDataRetrived: UpdateAppInfoResult.NewUserInfoFetched) {
        currentUserData = getDataForUser(freshDataRetrived.userId)
        if (firstFetchedUserId == null) {
            firstFetchedUserId = freshDataRetrived.userId
        }
        postFreshViewState()
    }

    fun actionNotifyStarted() {
        postFreshViewState()
    }

    fun inputActionRequestNextUser() {
        viewModelScope.launch {
            val currentUserId = currentUserData?.o1?.id ?: return@launch
            val nextUser = userRepository.getNextUser(currentUserId)
            if (nextUser != null) {
                currentUserData = getDataForUser(nextUser.id)
                postFreshViewState()
            } else {
                inputActionGetFreshUserAndDisplayIt()
            }
        }
    }

    fun inputActionGetFreshUserAndDisplayIt() {
        viewModelScope.launch {
            when (val updateAppInfoResult = informationUpdater.updateAppInfo()) {
                is UpdateAppInfoResult.NewUserInfoFetched -> handleUpdateAppInfoResultOk(updateAppInfoResult)
                is UpdateAppInfoResult.NewUserInfoWithoutIdFetched -> {
                    postBaseViewAction(BaseViewAction.ShowToast("User fetched has no Id", Toast.LENGTH_SHORT))
                }
                is UpdateAppInfoResult.UserInfoSourceError -> {
                    postBaseViewAction(BaseViewAction.ShowToast("Error. Couldn't retreive information", Toast.LENGTH_SHORT))
                }
            }
        }
    }

    fun inputActionRequestPreviousUser() {
        currentUserData?.o1?.let {
            viewModelScope.launch {
                userRepository.getPreviousUser(it.id)?.let {
                    currentUserData = getDataForUser(it.id)
                    postFreshViewState()
                } ?: postBaseViewAction(BaseViewAction.ShowToast("Couldnt find previous user", Toast.LENGTH_SHORT))
            }
        }
    }

    fun inputActionClickOpenUserLocation() = currentUserData?.o2?.coordenates?.apply {
        postViewAction(RandomUserViewAction.OpenMap(first, second))
    }

    private suspend fun getDataForUser(userId: String): Tuple3<User?, Location?, List<PhoneInfo>?> =
        Tuple3(
            userRepository.getUserById(userId),
            userLocationRepository.getLocationByUserId(userId),
            userPhoneRepository.findPhonesForUser(userId)
        )

}