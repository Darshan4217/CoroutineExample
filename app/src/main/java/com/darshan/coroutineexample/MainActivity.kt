package com.darshan.coroutineexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val IMAGE_URL =
        "https://raw.githubusercontent.com/DevTides/JetpackDogsApp/master/app/src/main/res/drawable/dog.png"
    //We want to update our image on imageview thats why we have taken Dispatchers.main
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch {
            //Download the image from network
            val originalDeferred = coroutineScope.async(Dispatchers.IO) { getOriginalBitmap() }
            //Want to wait to available the image
            val originalBitmap = originalDeferred.await()
            loadImage(originalBitmap)

            val originalDeferredBnW =
                coroutineScope.async(Dispatchers.Default) { applyFilter(originalBitmap) }
            loadImageBnW(originalDeferredBnW.await())
        }
    }

    private fun loadImage(originalBitmap: Bitmap) {
        progressBar.visibility = View.GONE
        imageView.apply {
            setImageBitmap(originalBitmap)
            visibility = View.VISIBLE
        }
    }

    private fun getOriginalBitmap() = URL(IMAGE_URL).openStream().use {
        BitmapFactory.decodeStream(it)
    }

    private fun applyFilter(bitmap: Bitmap) = Filter.apply(bitmap)

    private fun loadImageBnW(bitmap: Bitmap) {
        progressBar.visibility = View.GONE
        imageViewBlackAndWhite.apply {
            setImageBitmap(bitmap)
            visibility = View.VISIBLE
        }
    }
}
