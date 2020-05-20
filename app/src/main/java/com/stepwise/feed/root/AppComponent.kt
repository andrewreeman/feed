package com.stepwise.feed.root

import com.stepwise.feed.mainpage.MainPageActivity
import com.stepwise.feed.mainpage.MainPageModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainPageModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainPage: MainPageActivity)
}