package com.example.bluetoothdevicediscover.data.entity

class RandomUserApiResult(
    val gender: String,
    val name: NameApiResult,
    val location: LocationApiResult,
    val email: String,
    val dob: DateOfBirthApiResult,
    val phone: String,
    val cell: String,
    val id: IdApiResult,
    val picture: PictureApiResult
)

class NameApiResult(
    val title: String,
    val first: String,
    val last: String
)

class LocationApiResult(
    val street: StreetApiResult,
    val city: String,
    val state: String,
    val country: String,
    val postCode: String,
    val coordinates: CoordinatesApiResult
)

class StreetApiResult(
    val number: Int,
    val name: String
)

class CoordinatesApiResult(
    val latitude: String,
    val longitude: String
)

class DateOfBirthApiResult(
    val date: String
)

class IdApiResult(
    val name: String,
    val value: String?
)

class PictureApiResult(
    val large: String,
    val medium: String,
    val thumbnail: String
)