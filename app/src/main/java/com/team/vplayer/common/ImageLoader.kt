package com.team.vplayer.common

import android.content.Context
import coil.ImageLoader
import coil.decode.VideoFrameDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache

/** Coil 图片加载器（支持视频首帧做缩略图）。**成员 A 拥有**。 */
object ImageLoaderFactory {

    fun create(context: Context): ImageLoader =
        ImageLoader.Builder(context)
            .components { add(VideoFrameDecoder.Factory()) }
            .memoryCache {
                MemoryCache.Builder(context).maxSizePercent(0.15).build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.05)
                    .build()
            }
            .crossfade(true)
            .build()
}