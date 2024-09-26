package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object ImageLoader {

    /**
     * Load an image from a URL, first checking the cache.
     * @param context application context.
     * @param urlString The URL to load the image from.
     * @param onComplete Callback when the image is loaded (or null if failed).
     */
    fun loadImageFromUrl(context: Context, urlString: String, onComplete: (Bitmap?) -> Unit) {
        // Check the cache first
        ImageCache.getBitmapFromDiskCache(context, urlString)?.let {
            onComplete(it)
            return
        }

        // Load the image in a background thread if not cached
        thread {
            try {
                val url = URL(urlString)
                val connection = (url.openConnection() as HttpURLConnection).apply {
                    doInput = true
                    connect()
                }

                connection.inputStream.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    bitmap?.let {
                        ImageCache.addBitmapToDiskCache(context, urlString, it)  // Cache the image
                        onComplete(it)
                    } ?: onComplete(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                onComplete(null)
            }
        }
    }

    /**
     * Decode a sampled Bitmap from a stream.
     * @param inputStream The input stream to read from.
     * @param reqWidth The required width.
     * @param reqHeight The required height.
     */
    fun decodeSampledBitmapFromStream(
        inputStream: InputStream, reqWidth: Int, reqHeight: Int
    ): Bitmap? {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeStream(inputStream, null, options)

        // Calculate optimal inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false

        return BitmapFactory.decodeStream(inputStream, null, options)
    }

    /**
     * Calculate the optimal inSampleSize value.
     * @param options BitmapFactory.Options object with raw image data.
     * @param reqWidth The required width.
     * @param reqHeight The required height.
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Increment inSampleSize while the new dimensions are larger than the requested ones
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}
