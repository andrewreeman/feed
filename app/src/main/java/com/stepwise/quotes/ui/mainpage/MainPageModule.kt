package com.stepwise.quotes.ui.mainpage

import android.content.Context
import com.stepwise.quotes.api.MockApiServer
import com.stepwise.quotes.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class MainPageModule {
    @Provides
    fun providePresenter(model: MainPageMVP.Model, context: Context): MainPageMVP.Presenter {
        return Presenter(model, context.resources)
    }

    @Provides
    fun provideModel(repository: Repository): MainPageMVP.Model {
        return Model(repository)
    }
}