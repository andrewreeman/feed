package com.stepwise.quotes.root

import com.stepwise.quotes.repository.QuoteRepositoryModule
import com.stepwise.quotes.ui.mainpage.MainPageActivity
import com.stepwise.quotes.ui.mainpage.MainPageModule
import com.stepwise.quotes.ui.mainpage.Presenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MainPageModule::class, AppModule::class, QuoteRepositoryModule::class])
interface AppComponent {
    fun inject(mainPage: MainPageActivity)
    fun inject(presenter: Presenter)
}