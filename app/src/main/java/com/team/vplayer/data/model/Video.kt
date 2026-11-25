package com.team.vplayer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 一个本地视频文件。由成员 B 扫描 MediaStore 后填充。
 * 成员 A / C 可直接读取（只读，不要修改本文件）。
 */
@Entity(tableName = "videos")
data class Video(
    @PrimaryKey val id: Long,
    val path: String,
    val title: String,
    val durationMs: Long,
    val sizeBytes: Long,
    val width: Int,
    val height: Int,
    val folderName: String,
    val addedAt: Long,
)