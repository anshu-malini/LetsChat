package com.am.letschat.data.model;

import android.os.Parcelable
import androidx.room.*
import com.am.letschat.data.room.DataConverter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "user_chats", indices = [Index(value = ["userName"], unique = true)])
data class UserChats @Ignore constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_userId")
    var _userId: Long = 0,

    @ColumnInfo(name = "userName")
    var userName: String? = null,

    @ColumnInfo(name = "lastMessage")
    var lastMessage: String? = null,

    @ColumnInfo(name = "lastMsgTimestamp")
    var lastMsgTimestamp: Date? = null,

    @SerializedName("chatList")
    @TypeConverters(DataConverter::class)
    var chatList: MutableList<ChatItem>? = mutableListOf<ChatItem>()

) : Parcelable {
    constructor() : this(0, "", "", null, mutableListOf<ChatItem>())

    override fun toString(): String {
        return "UserChats(_userId=$_userId, userName=$userName, lastMessage=$lastMessage, lastMsgTimestamp=$lastMsgTimestamp, chatList=$chatList )"
    }
}