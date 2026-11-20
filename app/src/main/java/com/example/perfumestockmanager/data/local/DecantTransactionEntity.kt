package com.example.perfumestockmanager.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "decant_transactions")
data class DecantTransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val perfumeId: Int,

    val perfumeName: String,

    val amountMl: Int,

    val priceKzt: Int,

    val createdAt: Long = System.currentTimeMillis()
)