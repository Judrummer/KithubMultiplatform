package com.kithub.core.data

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.kithub.core.AndroidAppContext

actual class UserPreferenceImpl : UserPreference {

    private val sharedPreference
        get() = AndroidAppContext.app.getSharedPreferences("UserPreference", MODE_PRIVATE)

    override var username: String
        get() = sharedPreference.getString("username", "").orEmpty()
        set(value) {
            sharedPreference.edit {
                putString("username", value)
            }
        }
}