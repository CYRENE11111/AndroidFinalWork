package com.team.vplayer.player.service

import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

/**
 * 后台播放服务。**成员 C 拥有**。
 *
 * TODO(成员C 第3次)：创建 ExoPlayer 与 MediaSession，处理音频焦点与通知栏。
 */
class PlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null

    override fun onCreate() {
        super.onCreate()
        // TODO(成员C 第3次)：val player = ExoPlayer.Builder(this).build()
        // val mediaSession = MediaSession.Builder(this, player).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
        }
        mediaSession = null
        super.onDestroy()
    }
}
