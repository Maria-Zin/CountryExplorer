package ru.fefu.countryexplorer.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("all")
    suspend fun getAllCountries(
        @Query("fields") fields: String = "name,capital,population,area,region,flags"
    ): List<Country>

    @GET("name/{name}")
    suspend fun searchCountries(
        @Path("name") name: String,
        @Query("fields") fields: String = "name,capital,population,area,region,flags"
    ): List<Country>
}

object RetrofitInstance {
    private const val BASE_URL = "https://restcountries.com/v2/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}