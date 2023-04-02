package com.daud.notepad.base.network

import com.daud.notepad.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object BaseApiService {

    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BODY
        }
    )

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClientBuilder = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)

    private val chainReqBuilder = Request.Builder()
        .addHeader("Accept", "application/json")
        .addHeader("Content-Type", "application/json")

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        //.addCallAdapterFactory()

    private fun createRetrofit(baseUrl: String, needJwtBearer: Boolean): Retrofit {
        return retrofitBuilder.baseUrl(baseUrl).client(
            okHttpClientBuilder.apply {
                when (needJwtBearer) {
                    true -> addInterceptor(
                        Interceptor.invoke {
                            it.proceed(
                                chainReqBuilder
                                    .addHeader("", "")
                                    .build()
                            )
                        }
                    )
                    false -> addInterceptor(Interceptor.invoke { it.proceed(chainReqBuilder.build()) })
                }
            }.build()
        ).build()
    }

    fun <S> generate(
        serviceClass: Class<S>,
        serviceBaseURL: String = BuildConfig.BASE_URL,
        needJwtBearer: Boolean = true
    ): S {
        return createRetrofit(serviceBaseURL, needJwtBearer).create(serviceClass)
    }
}
