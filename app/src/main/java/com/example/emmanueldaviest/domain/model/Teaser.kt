package com.example.emmanueldaviest.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "teaser_table")
class Teaser(
    @PrimaryKey(autoGenerate = true)
    var teaserPrimaryKey: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("text")
    var text: String?,
    @SerializedName("isPaid")
    var isPaid: Boolean?,
    var isTabbed: Boolean,
    var isSubscribeView: Boolean,
    @SerializedName("type")
    var type: String?,
    var paidDescription: String,
    var categoryName: String


) {
    constructor() : this(null, "", "", false,false, false,"","","")
    constructor(isSubscribeView: Boolean) : this(null, "", "", false,false, isSubscribeView,"","","")
}



