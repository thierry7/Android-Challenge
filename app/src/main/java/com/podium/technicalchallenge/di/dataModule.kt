package com.podium.technicalchallenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.podium.technicalchallenge.local.MovieDao
import com.podium.technicalchallenge.local.MyDatabase
import com.podium.technicalchallenge.network.DefaultRepo
import com.podium.technicalchallenge.network.MovieRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object dataModule {
    @Provides
    @Singleton
    fun provideMyRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Set your desired log level here
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://podium-fe-challenge-2021.netlify.app/.netlify/functions/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    fun provideMyRepo(api: Retrofit, movieDao: MovieDao): MovieRepo {
        return  DefaultRepo(api, movieDao)

    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MyDatabase::class.java,
            "Movies.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideMovieDao(database: MyDatabase): MovieDao = database.movieDao()


}