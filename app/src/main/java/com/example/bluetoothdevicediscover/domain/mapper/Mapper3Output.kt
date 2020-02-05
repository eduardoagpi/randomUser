package com.example.bluetoothdevicediscover.domain.mapper

import com.example.bluetoothdevicediscover.domain.entity.Tuple3

abstract class Mapper3Output <I, O1, O2, O3> {
    abstract fun map (input: I): Tuple3<O1, O2, O3>
}