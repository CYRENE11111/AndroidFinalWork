package com.team.vplayer.data.scanner

import android.content.Context
import android.provider.MediaStore
import com.team.vplayer.data.model.Video
import com.team.vplayer.data.util.FilePathUtil
import com.team.vplayer.data.util.VideoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * 本地视频扫描。**成员 B 拥有**。
 * 用 MediaStore 查询设备上的视频，过滤无效项并按路径去重。
 */
class VideoScanner(private val context: Context) {

    suspend fun scan(): List<Video> = withContext(Dispatchers.IO) {
        val result = mutableListOf<Video>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.WIDTH,
            MediaStore.Video.Media.HEIGHT,
            MediaStore.Video.Media.DATE_ADDED,
        )

        runCatching {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                "${MediaStore.Video.Media.DATE_ADDED} DESC",
            )?.use { cursor ->
                val idCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val dataCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                val titleCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
                val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val widthCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH)
                val heightCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT)
                val dateCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

                while (cursor.moveToNext()) {
                    val path = cursor.getString(dataCol) ?: continue
                    val duration = cursor.getLong(durationCol)
                    if (duration <= 0L) continue
                    val file = File(path)
                    if (!file.exists()) continue
                    if (!VideoUtils.isVideoFile(path)) continue

                    result += Video(
                        id = cursor.getLong(idCol),
                        path = path,
                        title = cursor.getString(titleCol) ?: file.nameWithoutExtension,
                        durationMs = duration,
                        sizeBytes = cursor.getLong(sizeCol),
                        width = cursor.getInt(widthCol),
                        height = cursor.getInt(heightCol),
                        folderName = FilePathUtil.folderName(path),
                        addedAt = cursor.getLong(dateCol),
                    )
                }
            }
        }
        result.distinctBy { it.path }
    }
}