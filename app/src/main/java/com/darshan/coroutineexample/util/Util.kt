package com.darshan.coroutineexample.util

import android.content.Context
import android.widget.Toast

fun Context.showLongToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.showShortToast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
