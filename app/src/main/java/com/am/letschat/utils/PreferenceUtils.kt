package com.am.letschat.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.gson.Gson
import java.util.*

@SuppressLint("ApplySharedPref")
fun <T> Context.savePref(id: String = UUID.randomUUID().toString(), value: T): String {
    getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        .edit()
        .putString(id, Gson().toJson(value))
        .commit()
    return id
}

inline fun <reified T : Any> Context.getPref(id: String): T? {
    val preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    preferences.getString(id, null)?.let {
        return Gson().fromJson(it, T::class.java)
    }
    return null
}
