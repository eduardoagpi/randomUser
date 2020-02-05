package com.example.bluetoothdevicediscover.app.viewAction

abstract class BaseViewAction {
    data class ShowToast(val toastText: String, val toastLength: Int): BaseViewAction()
}
