package com.cs5540.weconnect.ui.profile


import com.cs5540.weconnect.R
import com.squareup.moshi.Json

data class Profile(
    val email: String,
    val userName: String?,
    val imageId:Int= R.drawable.kotlin,
    @Json(name="userId") val profileId: String )