package com.example.myapplication14.services

interface LogService {

    fun logNonFatalCrash(throwable: Throwable)

}