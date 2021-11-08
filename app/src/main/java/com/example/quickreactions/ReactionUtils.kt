package com.example.quickreactions

fun String.toEmoji() = String(Character.toChars(toInt(16)))