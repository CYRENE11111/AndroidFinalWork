package com.team.vplayer.data.repository

import com.team.vplayer.data.VideoRepository
import com.team.vplayer.data.model.Folder
import com.team.vplayer.data.model.HistoryItem
import com.team.vplayer.data.model.Progress
import com.team.vplayer.data.model.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * [VideoRepository] 的默认实现。**成员 B 拥有**。
 *
 * 目前是可编译的骨架，方法体待成员 B 按计划填充。
 */
class VideoRepositoryImpl : VideoRepository {

    override val videos: Flow<List<Video>> = emptyFlow()      // TODO(成员B 第3次)
    override val folders: Flow<List<Folder>> = emptyFlow()     // TODO(成员B 第3次)

    override suspend fun search(query: String): List<Video> {
        // TODO(成员B 第3次)
        return emptyList()
    }

    override suspend fun refresh() {
        // TODO(成员B 第2次)：触发扫描并写库
    }

    override val history: Flow<List<HistoryItem>> = emptyFlow()   // TODO(成员B 第4次)

    override suspend fun clearHistory() {
        // TODO(成员B 第4次)
    }

    override suspend fun removeHistory(id: Long) {
        // TODO(成员B 第4次)
    }

    override suspend fun isFavorite(id: Long): Boolean {
        // TODO(成员B 第4次)
        return false
    }

    override suspend fun toggleFavorite(id: Long) {
        // TODO(成员B 第4次)
    }

    override suspend fun saveProgress(id: Long, positionMs: Long, durationMs: Long) {
        // TODO(成员B 第5次)
    }

    override suspend fun getProgress(id: Long): Progress? {
        // TODO(成员B 第5次)
        return null
    }

    override var playbackSpeed: Float = 1.0f                    // TODO(成员B 第6次)
    override var autoPlayNext: Boolean = true                   // TODO(成员B 第6次)
    override var subtitleEnabled: Boolean = false               // TODO(成员B 第6次)
    override var hardwareDecode: Boolean = true                 // TODO(成员B 第6次)
}
