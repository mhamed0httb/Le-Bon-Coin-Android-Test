package fr.leboncoin.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.data.source.local.AlbumLocalDataSource
import fr.leboncoin.data.source.local.AlbumLocalDataSourceImpl
import fr.leboncoin.data.source.local.AppDatabase
import fr.leboncoin.data.source.local.dao.AlbumDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds
    @Singleton
    abstract fun bindAlbumLocalDataSource(
        albumLocalDataSourceImpl: AlbumLocalDataSourceImpl
    ): AlbumLocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
        }

        @Provides
        fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao {
            return appDatabase.albumDao()
        }
    }
}
