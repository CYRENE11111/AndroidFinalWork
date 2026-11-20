package com.team.vplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** 播放历史条目。由成员 B 维护。 */
@Entity(tableName = "history")
data class HistoryItem(
    @PrimaryKey val videoId: Long,
    val title: String,
    val path: String,
    val positionMs: Long,
    val durationMs: Long,
    val watchedAt: Long,
)
