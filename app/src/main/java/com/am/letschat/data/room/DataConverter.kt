package com.am.letschat.data.room

import androidx.lifecycle.LiveData
import androidx.room.TypeConverter
import com.am.letschat.data.model.ChatItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class DataConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun chatConvertToString(list: MutableList<ChatItem>?): String? {
        return if (list.isNullOrEmpty()) null
        else Gson().toJson(list)
    }

    @TypeConverter
    fun chatConvertFromString(value: String?): MutableList<ChatItem>? {
        val listType = object : TypeToken<MutableList<ChatItem>?>() {}.type
        return if (value.isNullOrEmpty()) null
        else Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun liveChatConvertToString(list: LiveData<MutableList<ChatItem>?>): String? {
        return if (list.value.isNullOrEmpty()) null
        else Gson().toJson(list)
    }

    @TypeConverter
    fun liveChatConvertFromString(value: String?): LiveData<MutableList<ChatItem>?>? {
        val listType = object : TypeToken<LiveData<MutableList<ChatItem>?>?>() {}.type
        return if (value.isNullOrEmpty()) null
        else Gson().fromJson(value, listType)
    }
}