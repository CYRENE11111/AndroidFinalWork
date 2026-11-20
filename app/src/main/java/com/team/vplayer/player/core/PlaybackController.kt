package com.team.vplayer.player.core

import android.app.Activity
import com.team.vplayer.data.model.Video
import kotlinx.coroutines.flow.StateFlow

/**
 * 播放控制统一入口。**由成员 C 拥有**，成员 A 只调用，不要改本文件。
 *
 * 生命周期约定：A 的播放页 onDestroy 只解绑（playerView.player = null），
 * **不调 release()**；播放器由 PlaybackService 持有，release 由 Service 销毁时调用。
 */
interface PlaybackController {

    val state: StateFlow<PlaybackState>

    fun setItems(videos: List<Video>, startIndex: Int)
    fun play()
    fun pause()
    fun playPause()
    fun seekTo(positionMs: Long)
    fun seekBy(deltaMs: Long)                       // 手势快进/快退
    fun next()
    fun previous()
    fun setSpeed(speed: Float)
    fun setRepeatMode(mode: RepeatMode)
    fun selectSubtitleTrack(index: Int)

    /** 由 PlaybackService 销毁时调用，不要由界面调用。 */
    fun release()

    // ---- PIP / 悬浮 ----
    fun enterPip(activity: Activity)
    fun showFloatingWindow(activity: Activity)
}
