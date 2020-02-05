package com.example.bluetoothdevicediscover.app.viewState

sealed class RandomUserViewState: BaseViewState() {

    object EmptyState: RandomUserViewState()

    data class UserDetailsViewState(
        val displayName: String,
        val profilePictureUrl: String,
        val address: String,
        val email: String,
        val age: String,
        val phones: String,
        val displayPrevious: Boolean
    ): RandomUserViewState()
}