package com.example.jettrips.flights.di

import android.app.Application
import androidx.room.Room
import com.example.jettrips.flights.model.FlightDao
import com.example.jettrips.flights.model.FlightDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlightDiModule {

    @Provides
    @Singleton
    fun provideCountryDatabase(applicationContext: Application): FlightDatabase {
        return Room.databaseBuilder(
            applicationContext,
            FlightDatabase::class.java,
            "flights"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideCountryDao(countryDatabase: FlightDatabase): FlightDao {
        return countryDatabase.get()
    }
}