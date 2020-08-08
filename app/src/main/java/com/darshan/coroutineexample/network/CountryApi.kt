package com.darshan.coroutineexample.network

import com.darshan.coroutineexample.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {
    @GET("DevTides/countries/master/countriesV2.json")
    suspend fun getCountries(): Response<List<Country>>
}