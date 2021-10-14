package com.am.letschat.utils

import java.text.SimpleDateFormat
import java.util.*

val LOCAL_DATE_FORMAT_PATTERN1 =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
val LOCAL_DATE_FORMAT_PATTERN2 = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
val PARSED_DATE_FORMAT = SimpleDateFormat("MMM dd, yyyy/hh.mm aa")

fun isSameDate(date1: Date?, date2: Date?): Boolean =
    LOCAL_DATE_FORMAT_PATTERN2.format(date1)
        .equals(LOCAL_DATE_FORMAT_PATTERN2.format(date2))

fun getCurrentTime(): Date? =
    LOCAL_DATE_FORMAT_PATTERN1.parse(LOCAL_DATE_FORMAT_PATTERN1.format(Date()))

