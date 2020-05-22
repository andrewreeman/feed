package com.stepwise.feed.root

import com.stepwise.feed.repository.QuoteRepositoryModule
import com.stepwise.feed.ui.mainpage.MainPageActivity
import com.stepwise.feed.ui.mainpage.MainPageModule
import com.stepwise.feed.ui.mainpage.Presenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainPageModule::class, AppModule::class, QuoteRepositoryModule::class])
interface AppComponent {
    fun inject(mainPage: MainPageActivity)
    fun inject(presenter: Presenter)
}