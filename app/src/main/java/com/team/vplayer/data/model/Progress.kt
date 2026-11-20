package com.team.vplayer.data.model

/** 播放进度（看到第几分）。由成员 B 存取。 */
data class Progress(
    val videoId: Long,
    val positionMs: Long,
    val durationMs: Long,
)
