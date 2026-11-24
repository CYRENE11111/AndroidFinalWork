package com.team.vplayer.data

import com.team.vplayer.data.model.Folder
import com.team.vplayer.data.model.HistoryItem
import com.team.vplayer.data.model.Progress
import com.team.vplayer.data.model.Video
import com.team.vplayer.data.util.SortType
import kotlinx.coroutines.flow.Flow

/**
 * 数据层统一入口。**由成员 B 拥有**，成员 A / C 只调用，不要改本文件。
 *
 * 列表用 Flow 暴露（Room 天然支持），界面收集后自动刷新。
 */
interface VideoRepository {

    // ---- 列表 ----
    val videos: Flow<List<Video>>
    val folders: Flow<List<Folder>>
    suspend fun search(query: String): List<Video>
    suspend fun refresh()                       // 触发一次重新扫描

    // ---- 历史 / 收藏 ----
    suspend fun addHistory(video: Video)
    val history: Flow<List<HistoryItem>>
    suspend fun clearHistory()
    suspend fun removeHistory(id: Long)
    suspend fun isFavorite(id: Long): Boolean
    suspend fun toggleFavorite(id: Long)
    val favoriteIds: Flow<List<Long>>

    // ---- 进度 ----
    suspend fun saveProgress(id: Long, positionMs: Long, durationMs: Long)
    suspend fun getProgress(id: Long): Progress?

    // ---- 队列 ----
    suspend fun saveQueue(videoIds: List<Long>)
    suspend fun getQueue(): List<Long>

    // ---- 小组件用：最近播放 ----
    suspend fun getRecentPlayed(limit: Int = 6): List<HistoryItem>

    // ---- 偏好（读写 SharedPreferences）----
    var playbackSpeed: Float
    var autoPlayNext: Boolean
    var subtitleEnabled: Boolean
    var hardwareDecode: Boolean

    // ---- 界面偏好（排序 / 列数）----
    var sortType: SortType
    var columnCount: Int
}