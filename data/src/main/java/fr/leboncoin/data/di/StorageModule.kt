package fr.leboncoin.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsPreferences

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppPreferences

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    private const val ANALYTICS_PREFS = "analytics_prefs"
    private const val APP_PREFS = "app_prefs"

    @Provides
    @Singleton
    @AnalyticsPreferences
    fun provideAnalyticsPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(ANALYTICS_PREFS, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @AppPreferences
    fun provideAppPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    }
}
