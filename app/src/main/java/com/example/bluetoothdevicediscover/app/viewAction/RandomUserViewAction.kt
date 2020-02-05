package com.example.bluetoothdevicediscover.app.viewAction

sealed class RandomUserViewAction: BaseViewAction() {
    data class OpenMap(val lat: Double, val long: Double): RandomUserViewAction()
}