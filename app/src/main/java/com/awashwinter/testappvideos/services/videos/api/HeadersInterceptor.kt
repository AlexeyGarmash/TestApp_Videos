package com.awashwinter.testappvideos.services.videos.api

import com.awashwinter.testappvideos.base.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization", Constants.PEXEL_API_KEY)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}