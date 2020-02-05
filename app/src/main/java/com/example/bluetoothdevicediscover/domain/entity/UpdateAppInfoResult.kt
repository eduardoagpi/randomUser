package com.example.bluetoothdevicediscover.domain.entity

sealed class UpdateAppInfoResult {
    data class NewUserInfoFetched(val userId: String): UpdateAppInfoResult()
    object NewUserInfoWithoutIdFetched: UpdateAppInfoResult()
    object UserInfoSourceError: UpdateAppInfoResult()
}