package com.example.medicalstoreuser.di

import android.content.Context
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.Domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  dataModule {

    @Singleton
    @Provides
       fun provideRepository() = Repository()

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ) = UserPreferenceManager(
        context = context
    )

}