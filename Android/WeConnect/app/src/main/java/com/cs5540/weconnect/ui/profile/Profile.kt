package com.cs5540.weconnect.ui.profile


import com.cs5540.weconnect.R
import com.squareup.moshi.Json

data class Profile(
    val email: String? = null,
    val userName: String? = null,
    val imageId:Int = R.drawable.kotlin,
    val idToken:String? = null,
    val error: Boolean = false,
    @Json(name="userId") val profileId: String? = null )