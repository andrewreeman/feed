package com.stepwise.feed.root

import android.app.Application
import com.stepwise.feed.api.MockApiServer
import com.stepwise.feed.repository.QuoteRepositoryModule
import com.stepwise.feed.ui.mainpage.MainPageModule
import okhttp3.HttpUrl

class App: Application() {
    val mockServer = MockApiServer()
    val baseUrl: HttpUrl? get() = mockServer.baseUrl

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        mockServer.start(resources)
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .mainPageModule(MainPageModule())
            .quoteRepositoryModule(QuoteRepositoryModule())
            .build()
    }
}