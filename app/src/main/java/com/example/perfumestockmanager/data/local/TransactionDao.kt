package com.example.perfumestockmanager.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(
        """
        SELECT *
        FROM decant_transactions
        ORDER BY createdAt DESC
        """
    )
    fun observeAllTransactions():
            Flow<List<DecantTransactionEntity>>


    @Insert
    suspend fun insertTransaction(
        transaction: DecantTransactionEntity
    )
}