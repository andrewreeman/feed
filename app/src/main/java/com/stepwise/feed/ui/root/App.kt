package com.stepwise.feed.ui.root

import android.app.Application
import com.stepwise.feed.repository.ContentRepositoryModule
import com.stepwise.feed.ui.mainpage.MainPageModule

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .mainPageModule(MainPageModule())
            .contentRepositoryModule(ContentRepositoryModule())
            .build()
    }
}