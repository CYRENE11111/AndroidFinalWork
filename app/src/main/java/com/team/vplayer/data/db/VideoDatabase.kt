package com.team.vplayer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.team.vplayer.data.model.Favorite
import com.team.vplayer.data.model.HistoryItem
import com.team.vplayer.data.model.Progress
import com.team.vplayer.data.model.QueueItem
import com.team.vplayer.data.model.Video

/** Room 数据库。**成员 B 拥有**。 */
@Database(
    entities = [
        Video::class,
        HistoryItem::class,
        Progress::class,
        Favorite::class,
        QueueItem::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao
    abstract fun historyDao(): HistoryDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun queueDao(): QueueDao
    abstract fun progressDao(): ProgressDao

    companion object {
        @Volatile
        private var INSTANCE: VideoDatabase? = null

        fun get(context: Context): VideoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    VideoDatabase::class.java,
                    "vplayer.db",
                ).build().also { INSTANCE = it }
            }
    }
}