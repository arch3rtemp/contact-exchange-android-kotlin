package com.sweeftdigital.contactsexchange.app.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "contact_table")
data class ContactEntity(
    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    val lastName: String,

    val job: String,

    val position: String,

    val email: String,

    @ColumnInfo(name = "phone_mobile")
    @SerializedName("phone_mobile")
    val phoneMobile: String,

    @ColumnInfo(name = "phone_office")
    @SerializedName("phone_office")
    val phoneOffice: String,

    @ColumnInfo(name = "create_date")
    @SerializedName("create_date")
    val createDate: Date,

    val color: Int,

    @ColumnInfo(name = "is_my")
    @SerializedName("is_my")
    val isMy: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
}
