package com.stepwise.feed.root

import android.app.Application
import com.stepwise.feed.mainpage.MainPageModule

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .mainPageModule(MainPageModule())
            .build()
    }
}