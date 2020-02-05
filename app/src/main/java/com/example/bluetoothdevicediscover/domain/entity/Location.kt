package com.example.bluetoothdevicediscover.domain.entity

data class Location(
    val street: String,
    val number: Int,
    val city: String,
    val coordenates: Pair<Double, Double>
)