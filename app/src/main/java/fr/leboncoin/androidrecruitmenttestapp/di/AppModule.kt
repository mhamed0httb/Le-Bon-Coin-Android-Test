package fr.leboncoin.androidrecruitmenttestapp.di

import android.content.Context
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageClient

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ImageClient
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "LeboncoinApp/1.0")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        @ImageClient okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                // Pass OkHttpClient via the OkHttp fetcher factory in Coil 3
                add(
                    OkHttpNetworkFetcherFactory(
                        callFactory = { okHttpClient }
                    )
                )
            }
            .crossfade(true)
            .build()
    }
}
