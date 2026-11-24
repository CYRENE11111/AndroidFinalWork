package com.team.vplayer.player.core

import com.team.vplayer.data.model.Video

/**
 * 播放状态快照。成员 C 维护，成员 A 收集后刷新 UI。
 */
data class PlaybackState(
    val current: Video? = null,
    val queue: List<Video> = emptyList(),
    val index: Int = 0,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
    val speed: Float = 1.0f,
    val error: String? = null,
    /** 定时关闭剩余毫秒数，0 表示未定时。 */
    val sleepTimerRemainingMs: Long = 0L,
)