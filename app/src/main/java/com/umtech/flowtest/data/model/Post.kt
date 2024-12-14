package com.umtech.flowtest.data.model

import androidx.annotation.Keep

@Keep
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)