package com.udacity.asteroidradar.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDomainEntity
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
@RequiresApi(Build.VERSION_CODES.O)
class AsteroidsRepository(private val database: AsteroidDatabase) {
    val asteroidAll: LiveData<List<Asteroid>> = database.asteroidDao.getAllAsteroid().map {
        it.asDomainModel()
    }

    val asteroidWeek: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroidByWeek().map {
        it.asDomainModel()
    }

    val asteroidDate: LiveData<List<Asteroid>> = database.asteroidDao.getAsteroidByDate().map {
        it.asDomainModel()
    }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            try {
                val data = Network.neoAPI.getFeed()
                val asteroids =
                    parseAsteroidsJsonResult(JSONObject(data)).asDomainEntity().toTypedArray()
                database.asteroidDao.insertAll(*asteroids)
            } catch (err: Exception) {
                Log.e("refreshData", err.printStackTrace().toString())
            }
        }
    }

    suspend fun refreshPOD():MutableLiveData<PictureOfDay> {
        val pictureOfDay = MutableLiveData<PictureOfDay>()
        withContext(Dispatchers.IO) {
            try {
                pictureOfDay.postValue(
                    Network.neoAPI.getPictureOfTheDay()
                )

            } catch (err: Exception) {
                Log.e("refreshPictureOfDay", err.printStackTrace().toString())
            }
        }
        return pictureOfDay
    }
}