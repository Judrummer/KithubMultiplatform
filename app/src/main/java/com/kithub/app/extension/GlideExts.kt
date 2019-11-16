package com.kithub.app.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}


fun ImageView.loadCircular(url: String) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}
