package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.utils.FilterAsteroid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {
//    private val _asteroidList = MutableLiveData<List<Asteroid>>((mutableListOf()))
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private var _filterAsteroid = MutableLiveData(FilterAsteroid.ALL)

    val asteroidList = _filterAsteroid.switchMap {
        when (it!!) {
            FilterAsteroid.WEEK -> asteroidsRepository.asteroidWeek
            FilterAsteroid.TODAY -> asteroidsRepository.asteroidDate
            else -> asteroidsRepository.asteroidAll
        }
    }

    fun onSetFilter(filter:FilterAsteroid){
        _filterAsteroid.postValue(filter)
    }
    init {
        viewModelScope.launch {
             asteroidsRepository.refreshData()
            _pictureOfDay.value =asteroidsRepository.refreshPOD().value
        }
    }
}