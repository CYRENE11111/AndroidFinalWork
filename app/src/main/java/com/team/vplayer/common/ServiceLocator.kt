package com.team.vplayer.common

import com.team.vplayer.data.VideoRepository
import com.team.vplayer.player.core.PlaybackController
import com.team.vplayer.player.core.PlayerManager

/**
 * 手写依赖注入入口（不引入 Hilt / Koin，避免课程项目被构建配置卡住）。
 *
 * - 成员 C 的 [PlayerManager] 是单例，直接用 [playback] 取。
 * - 成员 B 的 [VideoRepository] 实现由 B 创建后赋值给 [videoRepository]，
 *   由 A 在 App.onCreate 里调用注册。
 */
object ServiceLocator {

    @Volatile
    var videoRepository: VideoRepository? = null

    val playback: PlaybackController
        get() = PlayerManager

    /** 成员 B 在提供实现后调用此方法完成注册。 */
    fun registerVideoRepository(repository: VideoRepository) {
        videoRepository = repository
    }
}
