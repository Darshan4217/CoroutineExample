package com.darshan.coroutineexample.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.darshan.coroutineexample.R

fun ImageView.loadImage(uri: String?){
    Glide.with(this.context)
        .setDefaultRequestOptions(RequestOptions().error(R.mipmap.ic_launcher))
        .load(uri)
        .into(this)
}