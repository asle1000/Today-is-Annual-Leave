package com.dayoff.core.model

import HashtagCategory

data class Hashtag(
    val id: Long,
    val category: HashtagCategory,
    val emoji: String,
    val name: String,
)