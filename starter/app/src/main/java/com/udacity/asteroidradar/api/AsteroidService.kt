package com.udacity.asteroidradar.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.utils.convertDateToString
import com.udacity.asteroidradar.utils.toDate
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.nasa.gov/"
private const val API_KEY = "rJ0wo3oVkupEefelsKp3fZwn6NW0hf8pk4Blq2tz"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
interface NeoApiService {
    @RequiresApi(Build.VERSION_CODES.O)
    @GET("neo/rest/v1/feed")
    suspend fun getFeed(
        @Query("start_date") startDate: String = LocalDate.now().toDate().convertDateToString(),
        @Query("end_date") endDate: String = LocalDate.now().plusDays(6).toDate()
            .convertDateToString()
    ): String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(): PictureOfDay
}

object Network {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val url = chain
                .request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    val neoAPI = retrofit.create(NeoApiService::class.java)
}