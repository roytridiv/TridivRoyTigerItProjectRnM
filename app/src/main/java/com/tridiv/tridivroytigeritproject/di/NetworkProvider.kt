package com.tridiv.tridivroytigerit.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tridiv.tridivroytigeritproject.data.network.RestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkProvider {

    private val baseUrl = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().apply {
                addInterceptor { chain: Interceptor.Chain ->
                    val request =
                        chain.request().newBuilder()
                            .build()
                    chain.proceed(request)
                }

            }.build())
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi
                        .Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit) = retrofit.create(RestApiService::class.java)

}