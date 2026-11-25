package com.example.perfumestockmanager.data.remote

import retrofit2.http.GET

interface MarketApiService {

    @GET("products/category/fragrances")
    suspend fun getFragrances(): MarketResponse
}