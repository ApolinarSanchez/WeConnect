package com.cs5540.weconnect.ui.projects

import com.cs5540.weconnect.R
import com.squareup.moshi.Json

data class Project(
    val imageId:Int= R.drawable.kotlin,
    val title: String?,
    val categoryId: String?,
    val members: MutableList<String>?,
    val ownerId: String?,
    @Json(name="id") val projectId: String?,
    val description : String?

)