package com.example.tfl_gaffy.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class RoadModel(

    @SerializedName("$" + "type") val type: String,
    @SerializedName("id") val id: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("statusSeverity") val statusSeverity: String,
    @SerializedName("statusSeverityDescription") val statusSeverityDescription: String,
    @SerializedName("bounds") val bounds: String,
    @SerializedName("envelope") val envelope: String,
    @SerializedName("url") val url: String
)