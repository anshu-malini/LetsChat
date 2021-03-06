package com.am.letschat.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.am.letschat.data.model.ChatItem
import com.am.letschat.data.model.UserChats

@Database(
    entities = [(UserChats::class), (ChatItem::class)],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mobileDataDao(): MobileDataDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "am_chat_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
