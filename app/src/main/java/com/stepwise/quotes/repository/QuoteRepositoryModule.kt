package com.stepwise.quotes.repository

import com.stepwise.quotes.api.QuoteApi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class QuoteRepositoryModule {
    @Provides
    fun provideRepository(api: QuoteApi): Repository {
        return NetworkRepository(api)
//        return InMemoryRepository()
    }

    @Provides
    fun provideClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(baseUrlOfMockServer: HttpUrl?, client: OkHttpClient): Retrofit {
        return provideRetrofit(baseUrlOfMockServer?.toString() ?: "", client)
    }

    @Provides
    fun provideApi(retrofit: Retrofit): QuoteApi {
        return retrofit.create(QuoteApi::class.java)
    }

    @Suppress("SameParameterValue")
    private fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}