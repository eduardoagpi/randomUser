package com.example.bluetoothdevicediscover.domain.manager

import com.example.bluetoothdevicediscover.domain.entity.UpdateAppInfoResult

interface InformationUpdater {
    suspend fun updateAppInfo(): UpdateAppInfoResult
}