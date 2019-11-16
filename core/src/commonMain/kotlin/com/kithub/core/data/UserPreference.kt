package com.kithub.core.data

interface UserPreference{
    var username:String
}

expect class UserPreferenceImpl(): UserPreference

