package com.team.vplayer

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import coil.Coil
import com.team.vplayer.common.ImageLoaderFactory
import com.team.vplayer.data.DataManager
import com.team.vplayer.data.prefs.Prefs

/**
 * 应用入口。
 * - 初始化数据层（成员 B）
 * - 设置 Coil 图片加载器（支持视频帧缩略图）
 * - 应用夜间模式偏好
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DataManager.init(this)
        Coil.setImageLoader(ImageLoaderFactory.create(this))
        val dark = Prefs(this).darkMode
        AppCompatDelegate.setDefaultNightMode(
            if (dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO,
        )
    }
}