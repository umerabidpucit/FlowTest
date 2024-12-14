package com.umtech.flowtest.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class User(
    val userId: Int,
    val name: String,
    val url: String?,
    val thumbnailUrl: String?,
    var postCount: Int = 0,
    var posts: List<Post>? = ArrayList()
) : Parcelable