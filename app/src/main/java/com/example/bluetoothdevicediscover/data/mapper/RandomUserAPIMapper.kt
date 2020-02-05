package com.example.bluetoothdevicediscover.data.mapper

import com.example.bluetoothdevicediscover.data.entity.NoUserIdException
import com.example.bluetoothdevicediscover.data.entity.RandomUserApiResult
import com.example.bluetoothdevicediscover.domain.entity.*
import com.example.bluetoothdevicediscover.domain.mapper.Mapper3Output
import com.example.bluetoothdevicediscover.domain.entity.Tuple3
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object RandomUserAPIMapper: Mapper3Output<RandomUserApiResult, User, Location, List<PhoneInfo>>() {

    override fun map(input: RandomUserApiResult): Tuple3<User, Location, List<PhoneInfo>> {
        val userOutput = extractBasicUserInfo(input)
        val locationOutput = extractLocation(input)
        val userPhones = extractUserPhones(input)

        return Tuple3(
            userOutput,
            locationOutput,
            userPhones
        )
    }

    private fun extractBasicUserInfo(input: RandomUserApiResult) = input.run {
        val userId = input.id?.value ?: throw NoUserIdException()
        if (userId.isBlank()) throw NoUserIdException()
        User(
            userId,
            getUserName(input),
            getGender(input),
            input.email,
            getDateOfBirth(input),
            input.picture.medium
        )
    }

    private fun extractLocation(input: RandomUserApiResult): Location {
        return Location(
            input.location.street.name,
            input.location.street.number,
            input.location.city,
            Pair(
                input.location.coordinates.latitude.toDouble(),
                input.location.coordinates.longitude.toDouble()
            )
        )
    }

    private fun extractUserPhones(input: RandomUserApiResult): List<PhoneInfo> {
        return mutableListOf<PhoneInfo>().apply {
            add(PhoneInfo(input.cell, PhoneType.PhoneTypeCellphone))
            add(PhoneInfo(input.phone, PhoneType.PhoneTypeHouse))
        }
    }


    private fun getUserName(input: RandomUserApiResult) = UserName(
        input.name.title,
        input.name.first,
        input.name.last
    )

    private fun getGender(input: RandomUserApiResult) =
        when {
            input.gender == "male" -> Gender.MALE
            input.gender == "female" -> Gender.FEMALE
            else -> Gender.NOT_DEFINED
        }

    private fun getDateOfBirth(input: RandomUserApiResult): LocalDate {
        val localDateTime = Instant.parse(input.dob.date)
            .atZone(ZoneId.of("UTC"))
            .toLocalDateTime()
        return localDateTime.toLocalDate()
    }

}