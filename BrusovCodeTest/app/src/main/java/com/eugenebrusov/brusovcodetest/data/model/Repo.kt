package com.eugenebrusov.brusovcodetest.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Exposes repo info
 */
@Parcelize
data class Repo(@SerializedName("full_name") val fullName: String,
                val description: String,
                @SerializedName("updated_at") val updatedAt: String,
                @SerializedName("stargazers_count") val stars: Int,
                @SerializedName("html_url") val link: String): Parcelable