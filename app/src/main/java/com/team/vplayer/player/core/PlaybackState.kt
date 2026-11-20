package com.team.vplayer.player.core

import com.team.vplayer.data.model.Video

/** 重复模式。 */
enum class RepeatMode { OFF, ONE, ALL }

/**
 * 播放状态快照。成员 C 维护，成员 A 收集后刷新 UI。
 */
data class PlaybackState(
    val current: Video? = null,
    val index: Int = 0,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
    val speed: Float = 1.0f,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val error: String? = null,
)
