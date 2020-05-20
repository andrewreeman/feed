package com.stepwise.feed.repository

import dagger.Module
import dagger.Provides

@Module
class ContentRepositoryModule {
    @Provides
    fun provideRepository(): Repository {
        return InMemoryRepository()
    }
}