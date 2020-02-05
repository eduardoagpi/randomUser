package com.example.bluetoothdevicediscover.domain.manager

interface PermissionVerifier {

    fun checkPermission(permissionName: String): Boolean
}