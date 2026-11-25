package com.example.perfumestockmanager.ui

import com.example.perfumestockmanager.data.local.DecantTransactionEntity
import com.example.perfumestockmanager.data.local.PerfumeEntity

import com.example.perfumestockmanager.data.remote.MarketProductDto

data class PerfumeUiState(

    val perfumes:
    List<PerfumeEntity> = emptyList(),

    val transactions:
    List<DecantTransactionEntity> = emptyList(),

    val isLoading: Boolean = true,

    val message: String? = null,

    val marketPerfumes:
    List<MarketProductDto> = emptyList(),

    val isMarketLoading:
    Boolean = false,

    val marketError:
    String? = null,
)