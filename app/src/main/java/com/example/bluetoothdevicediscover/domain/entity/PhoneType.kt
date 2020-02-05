package com.example.bluetoothdevicediscover.domain.entity

sealed class PhoneType {
    object PhoneTypeCellphone: PhoneType()
    object PhoneTypeHouse: PhoneType()
    data class PhoneTypeCustom(val type: String): PhoneType()
}