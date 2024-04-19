package com.podium.technicalchallenge.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.podium.technicalchallenge.CastListConverter
import com.podium.technicalchallenge.DirectorConverter
import com.podium.technicalchallenge.GenresConverter
import com.podium.technicalchallenge.entity.LocalMovie

@Database(entities = [LocalMovie::class], version = 1)
@TypeConverters(GenresConverter::class, CastListConverter::class, DirectorConverter::class)
abstract class MyDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao // Define an abstract function to access DAO
    // Define more DAO functions if you have entities other than Movie
    // Example: abstract fun castDao(): CastDao
}
