package com.kithub.app

import android.app.Application
import com.kithub.core.AndroidAppContext

class MainApplication  : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidAppContext.app = this
    }
}