package com.stepwise.feed.root

import android.app.Application
import com.stepwise.feed.repository.ContentRepositoryModule
import com.stepwise.feed.ui.mainpage.MainPageModule
import kotlinx.coroutines.*
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer

class App: Application() {
    lateinit var appComponent: AppComponent
    lateinit var server: MockWebServer
    var baseUrl: HttpUrl? = null

    override fun onCreate() {
        super.onCreate()

        // this is a hacky way of simulating a real api as 'close to the network' as possible.
        // It isn't create as it contains a race condition for initialising the base url
        server = MockWebServer()
        Thread {
            server.start()
            baseUrl = server.url("/")
        }.start()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .mainPageModule(MainPageModule())
            .contentRepositoryModule(ContentRepositoryModule())
            .build()
    }
}