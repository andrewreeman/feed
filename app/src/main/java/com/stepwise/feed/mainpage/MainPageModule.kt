package com.stepwise.feed.mainpage

import android.content.Context
import com.stepwise.feed.mainpage.MainPageMVP
import com.stepwise.feed.mainpage.Model
import com.stepwise.feed.mainpage.Presenter
import dagger.Module
import dagger.Provides

@Module
class MainPageModule {

    @Provides
    fun providePresenter(model: MainPageMVP.Model, context: Context): MainPageMVP.Presenter {
        return Presenter(model, context.resources)
    }

    @Provides
    fun provideModel(): MainPageMVP.Model {
        return Model()
    }
}