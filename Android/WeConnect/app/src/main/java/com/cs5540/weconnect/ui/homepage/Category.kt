package com.cs5540.weconnect.ui.homepage

import com.cs5540.weconnect.R
import com.squareup.moshi.Json

data class Category(
    val name: String?,
    val imgId: Int= R.drawable.kotlin,
    @Json(name="id") val categoryId: String)