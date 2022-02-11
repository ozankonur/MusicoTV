package com.ozan.musicotv.di.apiModule

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ozan.musicotv.BuildConfig
import com.ozan.musicotv.data.model.LocalMusicResponse
import com.ozan.musicotv.data.network.NetworkManager
import com.ozan.musicotv.data.network.NetworkManagerImpl
import com.ozan.musicotv.data.network.entity.MusicResponse
import com.ozan.musicotv.data.network.retrofit.RetrofitInterface
import com.ozan.musicotv.data.network.retrofit.RetrofitService
import com.ozan.musicotv.data.network.retrofit.RetrofitServiceImpl
import com.ozan.musicotv.data.network.util.ApiMapper
import com.ozan.musicotv.util.mapper.EntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideRetrofitInterface(retrofit: Retrofit.Builder): RetrofitInterface {
        return retrofit
            .build()
            .create(RetrofitInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofitInterface: RetrofitInterface): RetrofitService {
        return RetrofitServiceImpl(retrofitInterface)
    }

    @Singleton
    @Provides
    fun provideApiMapper(): EntityMapper<MusicResponse, LocalMusicResponse> {
        return ApiMapper()
    }

    @Singleton
    @Provides
    fun provideNetworkManager(retrofitService: RetrofitService): NetworkManager {
        return NetworkManagerImpl(retrofitService, ApiMapper())
    }
}