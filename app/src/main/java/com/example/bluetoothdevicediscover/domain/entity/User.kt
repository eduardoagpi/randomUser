package com.example.bluetoothdevicediscover.domain.entity

import java.time.LocalDate

data class User(
    val id: String,
    val name: UserName,
    val gender: Gender,
    val email: String,
    val dob: LocalDate,
    val profileImage: String
)