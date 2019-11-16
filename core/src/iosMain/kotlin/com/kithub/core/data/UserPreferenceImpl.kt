package com.kithub.core.data

import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

actual class UserPreferenceImpl : UserPreference {

    private val userDefaults get() = NSUserDefaults(suiteName = "UserPreference")

    override var username: String
        get() = userDefaults.stringForKey("username").orEmpty()
        set(value) {
            userDefaults.setValue(value, forKey =  "username")
        }
}