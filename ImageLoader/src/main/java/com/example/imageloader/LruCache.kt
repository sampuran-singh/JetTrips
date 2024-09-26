package com.example.imageloader

import android.graphics.Bitmap
import android.util.LruCache

val cacheSize = 4 * 1024 * 1024 // 4MiB

val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
    override fun sizeOf(key: String, bitmap: Bitmap): Int {
        return bitmap.byteCount
    }
}

fun addBitmapToCache(key: String, bitmap: Bitmap) {
    if (getBitmapFromCache(key) == null) {
        memoryCache.put(key, bitmap)
    }
}

fun getBitmapFromCache(key: String): Bitmap? {
    return memoryCache.get(key)
}
