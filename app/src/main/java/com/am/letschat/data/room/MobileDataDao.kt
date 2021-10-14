package com.am.letschat.data.room

import androidx.room.*
import com.am.letschat.data.model.ChatItem
import com.am.letschat.data.model.UserChats

@Dao
interface MobileDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewData(userData: UserChats): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewSingleChat(items: ChatItem?): Long

    @Update
    fun updateUserData(userChat: UserChats)

    @Query("SELECT * FROM user_chats order by lastMsgTimestamp DESC")
    suspend fun getAllRecords(): MutableList<UserChats>

    @Query("SELECT * FROM chat_item where _childUserId=:userId order by _messageId")
    suspend fun getMessages(userId: Long): MutableList<ChatItem>

    @Query("SELECT * FROM chat_item where rowId=:rId")
    suspend fun getMessage(rId: Long): ChatItem

    @Query("SELECT * FROM user_chats where rowId=:rId")
    fun getUser(rId: Long): UserChats

    @Query("Delete FROM user_chats")
    suspend fun deleteUsers()

}
