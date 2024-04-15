package com.podium.technicalchallenge.di

import com.podium.technicalchallenge.DefaultRepo
import com.podium.technicalchallenge.MovieRepo
import com.podium.technicalchallenge.NetWorkDataSource
import com.podium.technicalchallenge.network.retrofit.GraphQLService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideMyRepo(api: Retrofit): MovieRepo {
        return  DefaultRepo(api)

    }
}