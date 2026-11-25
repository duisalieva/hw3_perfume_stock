package com.example.perfumestockmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfumestockmanager.data.PerfumeRepository
import com.example.perfumestockmanager.data.local.PerfumeEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfumeViewModel(

    private val repository:
    PerfumeRepository

) : ViewModel() {

    private val _uiState =
        MutableStateFlow(
            PerfumeUiState()
        )


    val uiState:
            StateFlow<PerfumeUiState> =
        _uiState.asStateFlow()


    init {

        viewModelScope.launch {

            repository.allPerfumes.collect {
                    perfumes ->

                _uiState.update {
                        currentState ->

                    currentState.copy(
                        perfumes = perfumes,
                        isLoading = false
                    )
                }
            }
        }


        viewModelScope.launch {

            repository.allTransactions.collect {
                    transactions ->

                _uiState.update {
                        currentState ->

                    currentState.copy(
                        transactions = transactions
                    )
                }
            }
        }
    }


    fun insertPerfume(
        perfume: PerfumeEntity
    ) {

        val error =
            validatePerfume(perfume)

        if (error != null) {

            showMessage(error)
            return
        }


        viewModelScope.launch {

            repository.insertPerfume(
                perfume
            )

            showMessage(
                "Perfume added"
            )
        }
    }


    fun updatePerfume(
        perfume: PerfumeEntity
    ) {

        val error =
            validatePerfume(perfume)

        if (error != null) {

            showMessage(error)
            return
        }


        viewModelScope.launch {

            repository.updatePerfume(
                perfume
            )

            showMessage(
                "Perfume updated"
            )
        }
    }


    fun deletePerfume(
        perfume: PerfumeEntity
    ) {

        viewModelScope.launch {

            repository.deletePerfume(
                perfume
            )

            showMessage(
                "Perfume deleted"
            )
        }
    }


    fun recordDecant(
        perfume: PerfumeEntity,
        amountMl: Int,
        priceKzt: Int
    ) {

        if (perfume.status != "owned") {

            showMessage(
                "Only owned perfumes can be decanted"
            )

            return
        }


        if (amountMl <= 0) {

            showMessage(
                "Amount must be greater than 0"
            )

            return
        }


        if (amountMl > perfume.remainingMl) {

            showMessage(
                "Not enough perfume remaining"
            )

            return
        }


        if (priceKzt < 0) {

            showMessage(
                "Price cannot be negative"
            )

            return
        }


        viewModelScope.launch {

            repository.recordDecant(
                perfume = perfume,
                amountMl = amountMl,
                priceKzt = priceKzt
            )

            showMessage(
                "Decant recorded"
            )
        }
    }


    fun clearMessage() {

        _uiState.update {
                currentState ->

            currentState.copy(
                message = null
            )
        }
    }


    private fun validatePerfume(
        perfume: PerfumeEntity
    ): String? {

        if (perfume.brand.isBlank()) {
            return "Brand is required"
        }


        if (perfume.name.isBlank()) {
            return "Name is required"
        }


        if (perfume.volumeMl <= 0) {
            return "Volume must be greater than 0"
        }


        if (perfume.remainingMl < 0) {
            return "Remaining volume cannot be negative"
        }


        if (
            perfume.remainingMl >
            perfume.volumeMl
        ) {

            return "Remaining volume cannot exceed bottle volume"
        }


        if (
            perfume.status !in listOf(
                "owned",
                "wishlist",
                "want_to_test"
            )
        ) {

            return "Invalid perfume status"
        }


        return null
    }


    private fun showMessage(
        message: String
    ) {

        _uiState.update {
                currentState ->

            currentState.copy(
                message = message
            )
        }
    }

    fun loadMarketPerfumes() {

        viewModelScope.launch {

            _uiState.update {
                    currentState ->

                currentState.copy(
                    isMarketLoading = true,
                    marketError = null
                )
            }

            try {

                val marketPerfumes =
                    repository
                        .getMarketPerfumes()

                _uiState.update {
                        currentState ->

                    currentState.copy(
                        marketPerfumes =
                            marketPerfumes,

                        isMarketLoading =
                            false,

                        marketError =
                            null
                    )
                }

            } catch (exception: Exception) {

                _uiState.update {
                        currentState ->

                    currentState.copy(
                        isMarketLoading =
                            false,

                        marketError =
                            exception.message
                                ?: "Failed to load market"
                    )
                }
            }
        }
    }
}