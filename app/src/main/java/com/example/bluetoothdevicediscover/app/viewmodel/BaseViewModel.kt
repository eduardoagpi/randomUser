package com.example.bluetoothdevicediscover.app.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.bluetoothdevicediscover.app.viewAction.BaseViewAction
import com.example.bluetoothdevicediscover.app.viewAction.single.SingleLiveEvent
import com.example.bluetoothdevicediscover.app.viewState.BaseViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : BaseViewState, VA : BaseViewAction>(application: Application) : AndroidViewModel(application) {

    private val viewStateLiveData = MutableLiveData<VS>()
    val stateLiveData: LiveData<VS>
        get() = viewStateLiveData

    // Events to the ui use SingleLiveEvent, in order to avoid duplicating the same event (when the usr makes a rotation change i.e.)
    private val viewActionLiveData = SingleLiveEvent<VA>()
    val actionsLiveData: LiveData<VA>
        get() = viewActionLiveData

    private val baseViewActionLiveData = SingleLiveEvent<BaseViewAction>()
    val baseActionsLiveData: LiveData<BaseViewAction>
        get() = baseViewActionLiveData

    protected abstract fun determineFreshViewState(): VS

    protected fun postFreshViewState() {
        viewModelScope.launch {
            viewStateLiveData.postValue(determineFreshViewState())
        }
    }

    protected fun postBaseViewAction(action: BaseViewAction) {
        viewModelScope.launch(Dispatchers.Main) {
            baseViewActionLiveData.value = action
        }
    }

    protected fun postViewAction(action: VA) {
        viewModelScope.launch(Dispatchers.Main) {
            viewActionLiveData.value = action
        }
    }
}
