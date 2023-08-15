package com.monzo.androidtest.di

import android.content.Context
import com.monzo.androidtest.data.remote.ArticleService
import com.monzo.androidtest.util.ConnectivityInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import javax.inject.Singleton

private const val BASE_URL = "https://content.guardianapis.com/"
private const val HEADER_API_KEY = "api-key"
// TODO Store this more securely
private const val API_KEY = "c976926c-4a23-434e-b3ab-1afcebe65967"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().apply {
            add(Date::class.java, Rfc3339DateJsonAdapter())
            add(KotlinJsonAdapterFactory())
        }.build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideConnectivityInterceptor(@ApplicationContext context: Context) =
        ConnectivityInterceptor(context)

    @Singleton
    @Provides
    fun provideAuthorisationInterceptor() = AuthorisationInterceptor()

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthorisationInterceptor,
        connectivityInterceptor: ConnectivityInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(connectivityInterceptor)
            addInterceptor(loggingInterceptor)
            addInterceptor(authInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        }.build()
    }

    @Provides
    @Singleton
    fun provideArticleRemoteService(retrofit: Retrofit): ArticleService =
        retrofit.create(ArticleService::class.java)
}

class AuthorisationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorisedRequestBuilder = request.newBuilder()
        authorisedRequestBuilder.addHeader(
            HEADER_API_KEY,
            API_KEY
        )
        return chain.proceed(authorisedRequestBuilder.build())
    }
}

