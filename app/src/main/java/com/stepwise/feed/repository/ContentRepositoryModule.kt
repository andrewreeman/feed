package com.stepwise.feed.repository

import com.google.gson.GsonBuilder
import com.stepwise.feed.api.ContentApi
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ContentRepositoryModule {
    companion object {
        const val BASE_URL: String = "http://localhost:8080"
    }

    @Provides
    fun provideRepository(api: ContentApi): Repository {
        val repo =  NetworkRepository(api)
        return InMemoryRepository()
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
        return provideRetrofit(baseUrlOfMockServer?.toString() ?: BASE_URL, client)
    }

    @Provides
    fun provideApi(retrofit: Retrofit): ContentApi {
        return retrofit.create(ContentApi::class.java)
    }

    @Suppress("SameParameterValue")
    private fun provideRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            //.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

            .build()
    }
}