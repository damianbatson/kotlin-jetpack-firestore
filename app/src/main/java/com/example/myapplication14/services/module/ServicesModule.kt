package com.example.myapplication14.services.module

import com.example.myapplication14.services.AuthService
import com.example.myapplication14.services.ConfigurationService
import com.example.myapplication14.services.HomeService
import com.example.myapplication14.services.LogService
import com.example.myapplication14.services.impl.HomesServiceImpl
import com.example.myapplication14.services.impl.AuthServiceImpl
import com.example.myapplication14.services.impl.ConfigurationServiceImpl
import com.example.myapplication14.services.impl.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {

    @Binds
    abstract fun provideAuthAccount(impl: AuthServiceImpl): AuthService

    @Binds
    abstract fun provideHomeAccount(impl: HomesServiceImpl): HomeService

    @Binds
    abstract fun provideLogAccount(impl: LogServiceImpl): LogService

    @Binds
    abstract fun provideConfigurationAccount(impl: ConfigurationServiceImpl): ConfigurationService
}