package com.bandtec.finfamily.api

import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val baseUrl = "http://ec2-54-82-171-69.compute-1.amazonaws.com/fin-family/api/v1/user/"
    private const val contentType = "application/json"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .method(original.method(), original.body())


            val request = requestBuilder.build()
            val response: Response = chain.proceed(chain.request())
            chain.proceed(request)
        }.build()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }


}
