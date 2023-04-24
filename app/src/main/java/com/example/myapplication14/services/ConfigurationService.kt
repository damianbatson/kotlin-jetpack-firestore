package com.example.myapplication14.services

interface ConfigurationService {

    suspend fun fetchConfiguration(): Boolean
    val hasEditIcon: Boolean
}