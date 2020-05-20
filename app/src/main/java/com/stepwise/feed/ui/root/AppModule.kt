package com.stepwise.feed.ui.root

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }
}