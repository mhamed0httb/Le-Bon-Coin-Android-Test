package fr.leboncoin.androidrecruitmenttestapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp
import fr.leboncoin.domain.logger.LoggerConfiguration
import javax.inject.Inject

@HiltAndroidApp
class PhotoApp : Application(), SingletonImageLoader.Factory {

    @Inject
    lateinit var loggerConfiguration: LoggerConfiguration

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        loggerConfiguration.setup(BuildConfig.DEBUG)
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
       return imageLoader
    }
}
