package com.example.perfumestockmanager.data.remote

data class MarketResponse(
    val products: List<MarketProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)