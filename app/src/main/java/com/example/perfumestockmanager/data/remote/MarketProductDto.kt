package com.example.perfumestockmanager.data.remote

data class MarketProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val thumbnail: String
)