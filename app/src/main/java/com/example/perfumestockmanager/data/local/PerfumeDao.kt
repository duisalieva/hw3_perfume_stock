package com.example.perfumestockmanager.data.local

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PerfumeDao {

    @Query(
        """
        SELECT * 
        FROM perfumes
        ORDER BY brand, name
        """
    )
    fun observeAllPerfumes(): Flow<List<PerfumeEntity>>


    @Insert
    suspend fun insertPerfume(
        perfume: PerfumeEntity
    )


    @Update
    suspend fun updatePerfume(
        perfume: PerfumeEntity
    )


    @Delete
    suspend fun deletePerfume(
        perfume: PerfumeEntity
    )
}