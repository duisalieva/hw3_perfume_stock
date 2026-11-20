package com.example.perfumestockmanager.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver

@Database(
    entities = [
        PerfumeEntity::class,
        DecantTransactionEntity::class
    ],
    version = 1
)
abstract class PerfumeDatabase : RoomDatabase() {

    abstract fun perfumeDao(): PerfumeDao

    abstract fun transactionDao(): TransactionDao


    companion object {

        @Volatile
        private var INSTANCE: PerfumeDatabase? = null


        fun getDatabase(
            context: Context
        ): PerfumeDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance =
                    Room.databaseBuilder<PerfumeDatabase>(
                        context.applicationContext,
                        "perfume_database"
                    )
                        .setDriver(
                            AndroidSQLiteDriver()
                        )
                        .build()

                INSTANCE = instance

                instance
            }
        }
    }
}