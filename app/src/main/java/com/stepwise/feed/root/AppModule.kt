package com.stepwise.feed.root

import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton


@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }

    @Singleton
    @Provides
    fun provideMockBaseUrl(): HttpUrl? {
        return app.baseUrl
    }
}