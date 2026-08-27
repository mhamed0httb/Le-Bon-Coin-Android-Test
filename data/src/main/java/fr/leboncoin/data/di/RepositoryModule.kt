package fr.leboncoin.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.leboncoin.data.repository.AlbumRepository
import javax.inject.Singleton
import fr.leboncoin.domain.repository.AlbumRepository as DomainAlbumRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAlbumRepository(repository: AlbumRepository): DomainAlbumRepository
}