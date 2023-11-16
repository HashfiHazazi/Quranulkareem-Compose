package com.example.quranappph1.data.remote.model

import com.example.quranappph1.data.remote.model.api_prayer_time.GetPrayerTimeSchedule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("day")
    suspend fun getPrayerTime(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ) : GetPrayerTimeSchedule

    companion object {
        private const val BASE_URL = "https://prayer-times-xi.vercel.app/api/prayer/"
        fun createApi(): ApiInterface {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}