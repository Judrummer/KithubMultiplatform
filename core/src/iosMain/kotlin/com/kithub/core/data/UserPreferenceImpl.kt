package com.kithub.core.data


actual class UserPreferenceImpl : UserPreference {

    //TODO: Create NSUserDefaults
    override var username: String
        get() {
            //TODO: get key username from NSUserDefaults
            return ""
        }
        set(value) {
            //TODO: set key username to NSUserDefaults by value
        }
}