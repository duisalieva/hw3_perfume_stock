package com.example.perfumestockmanager

import android.app.Application
import com.example.perfumestockmanager.data.PerfumeRepository
import com.example.perfumestockmanager.data.local.PerfumeDatabase
import com.example.perfumestockmanager.data.remote.RetrofitClient

class PerfumeApplication : Application() {

    val database by lazy {

        PerfumeDatabase.getDatabase(this)
    }


    val repository by lazy {

        PerfumeRepository(
            database = database,
            perfumeDao = database.perfumeDao(),
            transactionDao =
                database.transactionDao(),
            apiService =
                RetrofitClient.apiService
        )
    }
}