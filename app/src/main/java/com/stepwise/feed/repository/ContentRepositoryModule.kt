package com.stepwise.feed.repository

import com.stepwise.feed.api.ContentApi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ContentRepositoryModule {
    @Provides
    fun provideRepository(api: ContentApi): Repository {
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