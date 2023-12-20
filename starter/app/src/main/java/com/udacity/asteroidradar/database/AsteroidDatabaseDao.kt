package com.udacity.asteroidradar.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.udacity.asteroidradar.utils.convertDateToString
import com.udacity.asteroidradar.utils.toDate
import java.time.LocalDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Dao
interface AsteroidDatabaseDao {
    @Insert
    suspend fun insert(asteroid: AsteroidEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: AsteroidEntity)

    @Update
    suspend fun update(asteroid: AsteroidEntity)

    @Query("DELETE FROM asteroid_table")
    suspend fun deleteAll()

    @Query("DELETE FROM asteroid_table where id =:id")
    suspend fun deleteId(id: String)

    @Query("SELECT * FROM asteroid_table order by close_approach_date ASC")
    fun getAllAsteroid(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroid_table WHERE close_approach_date BETWEEN :startDate AND :endDate ORDER BY close_approach_date ASC")
    fun getAsteroidByWeek(
        startDate: Long = LocalDate.now().toDate().time,
        endDate: Long = LocalDate.now().plusDays(6).toDate().time
    ): LiveData<List<AsteroidEntity>>


    @Query("SELECT * FROM asteroid_table WHERE close_approach_date = :date ORDER BY close_approach_date ASC")
    fun getAsteroidByDate(date: Long = LocalDate.now().toDate().time): LiveData<List<AsteroidEntity>>

    @Query("SELECT * from asteroid_table WHERE id = :key")
    fun getNightWithId(key: Long): LiveData<AsteroidEntity>
}