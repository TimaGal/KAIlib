package com.example.kailibrary

import android.app.Application
import android.content.Context

class MyApp: Application() {


    override fun onCreate() {
        super.onCreate()

    }
}

//val Context.app1Component: App1Component get() = when(this){ is MyApp -> app1Component else -> this.applicationContext.app1Component}

