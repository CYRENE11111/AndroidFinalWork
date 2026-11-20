package com.team.vplayer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.team.vplayer.data.model.Video
import kotlinx.coroutines.flow.Flow

/** 视频表 DAO。**成员 B 拥有**。 */
@Dao
interface VideoDao {

    @Query("SELECT * FROM videos ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<Video>>

    @Query("SELECT * FROM videos WHERE id = :id")
    suspend fun getById(id: Long): Video?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: List<Video>)

    @Query("DELETE FROM videos")
    suspend fun clear()
}
