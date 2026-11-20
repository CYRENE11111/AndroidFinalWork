package com.team.vplayer.data.scanner

import android.content.Context
import com.team.vplayer.data.model.Video

/**
 * 本地视频扫描。**成员 B 拥有**。
 *
 * TODO(成员B 第2次)：权限检查、MediaStore.Video 查询、过滤去重、按文件夹分组。
 */
class VideoScanner(private val context: Context) {

    suspend fun scan(): List<Video> {
        // TODO(成员B 第2次)
        return emptyList()
    }
}
