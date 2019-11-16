package com.kithub.core.data

actual class UserPreferenceImpl : UserPreference {
    //TODO: Create Android SharedPreference (get context by type "AndroidAppContext.app")
    override var username: String
        get() {
            //TODO: get key username from SharedPreference
            return ""
        }
        set(value) {
            //TODO: set key username to SharedPreference by value
        }
}