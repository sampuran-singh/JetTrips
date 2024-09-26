package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import java.io.File

object ImageCache {
//    private val cacheSize =
//        (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8  // 1/8th of available memory

    private val cacheSize = 8 * 1024 * 1024 // 4MiB

    private val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount / 1024
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

    fun addBitmapToDiskCache(context: Context, urlString: String, bitmap: Bitmap) {
        val cacheDir = File(context.cacheDir, "image_cache")
        if (!cacheDir.exists()) cacheDir.mkdirs()

        val file = File(cacheDir, urlString.hashCode().toString())
        file.outputStream().use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
    }

    fun getBitmapFromDiskCache(context: Context, urlString: String): Bitmap? {
        val cacheDir = File(context.cacheDir, "image_cache")
        val file = File(cacheDir, urlString.hashCode().toString())
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.path)
        }
        return null
    }

}
