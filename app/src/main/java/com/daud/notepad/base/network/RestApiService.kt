package com.daud.notepad.base.network

import com.daud.notepad.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RestApiService {
    private val loggingInterceptor: HttpLoggingInterceptor = if(BuildConfig.DEBUG) {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(UserInterceptor())
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    private val moshi = Moshi.Builder()
        .add(ThrowableAdapter())
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        var retrofit: Retrofit? = null
        return if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(ResponseAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            retrofit
        } else {
            retrofit
        }
    }

    fun <S> generate(serviceClass: Class<S>, serviceBaseURL: String): S {
        return createRetrofit(serviceBaseURL).create(serviceClass)
    }
}
