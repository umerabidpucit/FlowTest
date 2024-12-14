package com.umtech.flowtest.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Post(
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val body: String?
) : Parcelable