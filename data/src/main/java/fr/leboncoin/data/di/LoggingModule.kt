package fr.leboncoin.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.data.logger.TimberLogger
import fr.leboncoin.domain.logger.Logger
import fr.leboncoin.domain.logger.LoggerConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggingModule {

    @Binds
    @Singleton
    abstract fun bindLogger(timberLogger: TimberLogger): Logger

    @Binds
    @Singleton
    abstract fun bindLoggerConfiguration(timberLogger: TimberLogger): LoggerConfiguration
}
