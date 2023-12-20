package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.utils.Converters
import com.udacity.asteroidradar.utils.convertDateToString
import java.util.Date

@Entity(tableName = "asteroid_table")
@TypeConverters(Converters::class)
data class AsteroidEntity(
    @PrimaryKey
    var id: Long = 0L,

    @ColumnInfo(name = "code_name")
    var codename: String,

    @ColumnInfo(name = "close_approach_date")
    var closeApproachDate: Date,

    @ColumnInfo(name = "absolute_magnitude")
    var absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    var estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity")
    var relativeVelocity: Double,

    @ColumnInfo(name = "distance_from_earth")
    var distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous")
    var isPotentiallyHazardous: Boolean = true,
)

fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename, closeApproachDate = it.closeApproachDate.convertDateToString(),
            absoluteMagnitude = it.absoluteMagnitude, estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity, distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}