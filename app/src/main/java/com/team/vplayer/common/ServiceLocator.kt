package com.team.vplayer.common

import com.team.vplayer.data.VideoRepository
import com.team.vplayer.player.core.PlaybackController
import com.team.vplayer.player.core.PlayerManager

/**
 * 手写依赖注入入口（不引入 Hilt / Koin）。
 *
 * - 成员 C 的 [PlayerManager] 是单例，直接用 [playback] 取。
 * - 成员 B 的 [VideoRepository] 实现由 B 创建后通过 [registerVideoRepository] 注册。
 */
object ServiceLocator {

    @Volatile
    var videoRepository: VideoRepository? = null

    val playback: PlaybackController
        get() = PlayerManager

    /** 非空取值，未初始化会给出明确报错。 */
    val repository: VideoRepository
        get() = videoRepository
            ?: error("VideoRepository 未初始化，请先调用 DataManager.init(context)")

    fun registerVideoRepository(repository: VideoRepository) {
        videoRepository = repository
    }
}