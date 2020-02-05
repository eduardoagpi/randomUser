package com.example.bluetoothdevicediscover.app.presenter

import com.example.bluetoothdevicediscover.app.viewState.RandomUserViewState
import com.example.bluetoothdevicediscover.domain.entity.Location
import com.example.bluetoothdevicediscover.domain.entity.PhoneInfo
import com.example.bluetoothdevicediscover.domain.entity.PhoneType
import com.example.bluetoothdevicediscover.domain.entity.User
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object RandomUserPresenter {

    fun generateUserDetailsViewState(
        user: User?,
        userPhones: List<PhoneInfo>?,
        userLocation: Location?,
        firstUserId: String?
    ): RandomUserViewState.UserDetailsViewState {

        val displayName = user?.let { "${it.name.title} ${it.name.name} ${it.name.lastName}" } ?: "Name not defined"
        val profilePictureUrl = user?.let { it.profileImage } ?: ""
        val address = userLocation?.let { "${it.street} ${it.number} ${it.city}" } ?: "Address not defined"
        val email = user?.email ?: "Email not defined"
        val age: String = user?.let {
            ChronoUnit.YEARS.between(it.dob, LocalDate.now()).toString() .plus(" years")
        } ?: "Age not defined"
        val phones = formatPhonesInformation(userPhones)
        val displayPrevious = firstUserId != null && user?.id != null && firstUserId != user.id


        return RandomUserViewState.UserDetailsViewState(
            displayName,
            profilePictureUrl,
            address,
            email,
            age,
            phones,
            displayPrevious)
    }

    private fun formatPhonesInformation(phoneList: List<PhoneInfo>?): String {
        return if (phoneList.isNullOrEmpty()) {
            "No Phones defined"
        } else {
            phoneList.joinTo(separator = ",\n", transform = {
                val suffix = when(it.phoneType) {
                    is PhoneType.PhoneTypeHouse -> "Home"
                    is PhoneType.PhoneTypeCellphone -> "Cellphone"
                    is PhoneType.PhoneTypeCustom -> it.phoneType.type
                }
                "${it.number} ($suffix)"
            }, buffer = StringBuilder()).toString()
        }
    }
}