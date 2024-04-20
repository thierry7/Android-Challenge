package com.podium.technicalchallenge.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.podium.technicalchallenge.Converters
import com.podium.technicalchallenge.entity.LocalMovie

@Database(entities = [LocalMovie::class], version = 1)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao // Define an abstract function to access DAO
    // Define more DAO functions if you have entities other than Movie
    // Example: abstract fun castDao(): CastDao
}
