package com.am.letschat.data.model;

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "chat_item")
data class ChatItem @Ignore constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_messageId")
    var _messageId: Long = 0,

    @ColumnInfo(name = "_childUserId")
    var _childUserId: Long? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "message")
    var message: String? = null,

    @ColumnInfo(name = "timestamp")
    var timestamp: Date? = null,

    @ColumnInfo(name = "direction")
    var direction: String? = null

) : Parcelable {
    constructor() : this(0, 0, "", "", null, "")

    override fun toString(): String {
        return "ChatItem(_messageId=$_messageId, _childUserId=$_childUserId, name=$name, message=$message,timestamp=$timestamp, direction=$direction)"
    }
}