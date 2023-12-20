package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.utils.convertDateToString
import com.udacity.asteroidradar.utils.toDate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

fun List<Asteroid>.asDomainEntity(): List<AsteroidEntity> {
    return map {
        AsteroidEntity(
            id = it.id,
            codename = it.codename, closeApproachDate = it.closeApproachDate.toDate(),
            absoluteMagnitude = it.absoluteMagnitude, estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity, distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}