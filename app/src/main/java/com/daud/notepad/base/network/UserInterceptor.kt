package com.daud.notepad.base.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgentRequest = chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(userAgentRequest)
    }
}
