package com.example.perfumestockmanager.ui

import com.example.perfumestockmanager.data.local.DecantTransactionEntity
import com.example.perfumestockmanager.data.local.PerfumeEntity

data class PerfumeUiState(

    val perfumes:
    List<PerfumeEntity> = emptyList(),

    val transactions:
    List<DecantTransactionEntity> = emptyList(),

    val isLoading: Boolean = true,

    val message: String? = null
)