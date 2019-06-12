package com.example.emmanueldaviest.domain.model

import com.google.gson.annotations.SerializedName


class Headline {

        @SerializedName("name")
        var name: String? = ""
        @SerializedName("teasers")
        var teasers: List<Teaser>? = null

}