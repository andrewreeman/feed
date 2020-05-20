package com.stepwise.feed.mainpage

import com.stepwise.feed.mainpage.MainPageMVP
import com.stepwise.feed.mainpage.Model
import com.stepwise.feed.mainpage.Presenter
import dagger.Module
import dagger.Provides

@Module
class MainPageModule {

    @Provides
    fun providePresenter(model: MainPageMVP.Model): MainPageMVP.Presenter {
        return Presenter(model)
    }

    @Provides
    fun provideModel(): MainPageMVP.Model {
        return Model()
    }
}