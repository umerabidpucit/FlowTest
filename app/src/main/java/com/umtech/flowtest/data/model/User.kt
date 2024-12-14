package com.umtech.flowtest.data.model

import androidx.annotation.Keep

@Keep
data class User(
    val userId: Int,
    val name: String,
    val url: String,
    val thumbnailUrl: String,
    var postCount: Int = 0
)