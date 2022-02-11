package com.ozan.musicotv.di.repositoryModule

import com.ozan.musicotv.data.network.NetworkManager
import com.ozan.musicotv.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMusicoRepository(
            networkManager: NetworkManager
    ): Repository {
        return Repository(networkManager)
    }
}