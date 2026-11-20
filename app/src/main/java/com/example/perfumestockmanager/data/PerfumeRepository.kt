package com.example.perfumestockmanager.data

import androidx.room3.withWriteTransaction
import com.example.perfumestockmanager.data.local.DecantTransactionEntity
import com.example.perfumestockmanager.data.local.PerfumeDao
import com.example.perfumestockmanager.data.local.PerfumeDatabase
import com.example.perfumestockmanager.data.local.PerfumeEntity
import com.example.perfumestockmanager.data.local.TransactionDao
import kotlinx.coroutines.flow.Flow

class PerfumeRepository(

    private val database: PerfumeDatabase,

    private val perfumeDao: PerfumeDao,

    private val transactionDao: TransactionDao

) {

    val allPerfumes: Flow<List<PerfumeEntity>> =
        perfumeDao.observeAllPerfumes()


    val allTransactions:
            Flow<List<DecantTransactionEntity>> =
        transactionDao.observeAllTransactions()


    suspend fun insertPerfume(
        perfume: PerfumeEntity
    ) {

        perfumeDao.insertPerfume(perfume)
    }


    suspend fun updatePerfume(
        perfume: PerfumeEntity
    ) {

        perfumeDao.updatePerfume(perfume)
    }


    suspend fun deletePerfume(
        perfume: PerfumeEntity
    ) {

        perfumeDao.deletePerfume(perfume)
    }


    suspend fun recordDecant(
        perfume: PerfumeEntity,
        amountMl: Int,
        priceKzt: Int
    ) {

        database.withWriteTransaction {

            val updatedPerfume =
                perfume.copy(
                    remainingMl =
                        perfume.remainingMl - amountMl
                )

            perfumeDao.updatePerfume(
                updatedPerfume
            )


            val transaction =
                DecantTransactionEntity(

                    perfumeId = perfume.id,

                    perfumeName =
                        "${perfume.brand} ${perfume.name}",

                    amountMl = amountMl,

                    priceKzt = priceKzt
                )


            transactionDao.insertTransaction(
                transaction
            )
        }
    }
}