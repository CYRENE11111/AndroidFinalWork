package com.team.vplayer.player.core

import android.app.Activity
import com.team.vplayer.data.model.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 播放内核单例。**成员 C 拥有**。
 *
 * 目前是可编译的骨架：维护队列与状态流，但还没接 ExoPlayer。
 * TODO(成员C 第2次)：接入 ExoPlayer，把状态与播放器同步。
 */
object PlayerManager : PlaybackController {

    private val _state = MutableStateFlow(PlaybackState())
    override val state: StateFlow<PlaybackState> = _state.asStateFlow()

    private val items = mutableListOf<Video>()

    override fun setItems(videos: List<Video>, startIndex: Int) {
        items.clear()
        items.addAll(videos)
        _state.value = _state.value.copy(
            current = items.getOrNull(startIndex),
            index = startIndex.coerceIn(0, (items.size - 1).coerceAtLeast(0)),
        )
    }

    override fun play() {
        _state.value = _state.value.copy(isPlaying = true, error = null)
    }

    override fun pause() {
        _state.value = _state.value.copy(isPlaying = false)
    }

    override fun playPause() {
        if (_state.value.isPlaying) pause() else play()
    }

    override fun seekTo(positionMs: Long) {
        _state.value = _state.value.copy(positionMs = positionMs.coerceAtLeast(0L))
    }

    override fun seekBy(deltaMs: Long) {
        seekTo(_state.value.positionMs + deltaMs)
    }

    override fun next() {
        if (items.isEmpty()) return
        val nextIndex = (_state.value.index + 1).mod(items.size)
        _state.value = _state.value.copy(current = items[nextIndex], index = nextIndex)
    }

    override fun previous() {
        if (items.isEmpty()) return
        val prevIndex = (_state.value.index - 1).mod(items.size)
        _state.value = _state.value.copy(current = items[prevIndex], index = prevIndex)
    }

    override fun setSpeed(speed: Float) {
        _state.value = _state.value.copy(speed = speed)
    }

    override fun setRepeatMode(mode: RepeatMode) {
        _state.value = _state.value.copy(repeatMode = mode)
    }

    override fun selectSubtitleTrack(index: Int) {
        // TODO(成员C 第6次)：字幕轨选择
    }

    override fun release() {
        // TODO(成员C 第3次)：释放 ExoPlayer
    }

    override fun enterPip(activity: Activity) {
        // TODO(成员C 第8次)：进入画中画
    }

    override fun showFloatingWindow(activity: Activity) {
        // TODO(成员C 第8次)：悬浮窗
    }
}
