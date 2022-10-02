package com.android.nasagalleryapp.model

import com.google.gson.annotations.SerializedName

data class DetailDataResponse(

    @field:SerializedName("DetailDataResponse")
    val detailDataResponseItemList: List<DetailDataResponseItem?>? = null
)

data class DetailDataResponseItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("copyright")
    val copyright: String? = null,

    @field:SerializedName("media_type")
    val mediaType: String? = null,

    @field:SerializedName("hdurl")
    val hdurl: String? = null,

    @field:SerializedName("service_version")
    val serviceVersion: String? = null,

    @field:SerializedName("explanation")
    val explanation: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)
