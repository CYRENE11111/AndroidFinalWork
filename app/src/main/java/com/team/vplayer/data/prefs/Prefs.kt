package com.team.vplayer.data.prefs

import android.content.Context

/**
 * SharedPreferences 封装。**成员 B 拥有**。
 *
 * TODO(成员B 第6次)：倍速、自动连播、字幕开关、硬解/软解、夜间模式、定时关闭时长。
 */
class Prefs(context: Context) {

    private val sp = context.getSharedPreferences("vplayer_prefs", Context.MODE_PRIVATE)

    // TODO(成员B 第6次)
}
