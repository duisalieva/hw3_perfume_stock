package com.example.perfumestockmanager.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "perfumes")
data class PerfumeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val brand: String,

    val name: String,

    val concentration: String?,

    val volumeMl: Int,

    val remainingMl: Int,

    val fragranceFamily: String?,

    val topNotes: String?,

    val heartNotes: String?,

    val baseNotes: String?,

    val status: String
)