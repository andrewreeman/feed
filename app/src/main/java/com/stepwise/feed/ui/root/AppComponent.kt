package com.stepwise.feed.ui.root

import com.stepwise.feed.repository.ContentRepositoryModule
import com.stepwise.feed.ui.mainpage.MainPageActivity
import com.stepwise.feed.ui.mainpage.MainPageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainPageModule::class, AppModule::class, ContentRepositoryModule::class])
interface AppComponent {
    fun inject(mainPage: MainPageActivity)
}