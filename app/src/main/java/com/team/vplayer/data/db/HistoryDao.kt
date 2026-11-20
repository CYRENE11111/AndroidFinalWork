package com.team.vplayer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.team.vplayer.data.model.HistoryItem
import kotlinx.coroutines.flow.Flow

/** 历史表 DAO。**成员 B 拥有**。 */
@Dao
interface HistoryDao {

    @Query("SELECT * FROM history ORDER BY watchedAt DESC")
    fun observeAll(): Flow<List<HistoryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: HistoryItem)

    @Query("DELETE FROM history WHERE videoId = :videoId")
    suspend fun deleteById(videoId: Long)

    @Query("DELETE FROM history")
    suspend fun clear()
}
