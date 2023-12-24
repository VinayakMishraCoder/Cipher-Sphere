package com.example.ciphersphere_cryptocurrencytracker.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    fun getCurrentDateTime(): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
}