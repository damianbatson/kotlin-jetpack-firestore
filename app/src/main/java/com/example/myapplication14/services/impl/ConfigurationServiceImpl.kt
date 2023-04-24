package com.example.myapplication14.services.impl

import com.example.myapplication14.BuildConfig
import com.example.myapplication14.services.ConfigurationService
import com.example.myapplication14.services.trace
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.example.myapplication14.R.xml as AppConfig

class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {
    private val remoteConfig
        get() = Firebase.remoteConfig


    init {
        if (BuildConfig.DEBUG) {
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 0
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
        }

        remoteConfig.setDefaultsAsync(AppConfig.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean =
        trace(FETCH_CONFIG_TRACE) { remoteConfig.fetchAndActivate().await() }

    override val hasEditIcon: Boolean
        get() = remoteConfig[HAS_EDIT_ICON_KEY].asBoolean()


    companion object {
        private const val HAS_EDIT_ICON_KEY = "has_edit_icon"
        private const val FETCH_CONFIG_TRACE = "fetchConfig"
    }
}