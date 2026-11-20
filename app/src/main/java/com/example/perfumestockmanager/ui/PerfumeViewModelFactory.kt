package com.example.perfumestockmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perfumestockmanager.data.PerfumeRepository

class PerfumeViewModelFactory(

    private val repository:
    PerfumeRepository

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                PerfumeViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return PerfumeViewModel(
                repository
            ) as T
        }


        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}