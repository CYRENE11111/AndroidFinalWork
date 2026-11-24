package com.team.vplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** 播放进度（看到第几分）。**成员 B 拥有**。 */
@Entity(tableName = "progress")
data class Progress(
    @PrimaryKey val videoId: Long,
    val positionMs: Long,
    val durationMs: Long,
)