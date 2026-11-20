package com.team.vplayer.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team.vplayer.databinding.ActivityPlayerBinding

/**
 * 播放页。**成员 A 拥有**。
 *
 * TODO(成员A 第3-5次)：绑定 ServiceLocator.playback，控制条、进度、手势、菜单。
 */
class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        // 只解绑，不调用 release（release 由 PlaybackService 调用）
        binding.playerView.player = null
        super.onDestroy()
    }
}
