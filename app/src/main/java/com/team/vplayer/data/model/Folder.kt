package com.team.vplayer.data.model

/** 视频文件夹分组（由 B 从视频列表派生，不需要建表）。 */
data class Folder(
    val name: String,
    val path: String,
    val videoCount: Int,
    val coverVideoId: Long?,
)
