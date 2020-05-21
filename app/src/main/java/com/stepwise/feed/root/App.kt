package com.stepwise.feed.root

import android.app.Application
import com.stepwise.feed.R
import com.stepwise.feed.api.MockApiServer
import com.stepwise.feed.repository.ContentRepositoryModule
import com.stepwise.feed.ui.mainpage.MainPageModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import java.util.concurrent.Executors

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
            .contentRepositoryModule(ContentRepositoryModule())
            .build()
    }
}