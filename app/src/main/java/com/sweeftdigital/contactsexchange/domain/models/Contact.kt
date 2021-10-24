package com.sweeftdigital.contactsexchange.domain.models

import android.os.Build
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "contact_table")
class Contact(
    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    val firstName: String,

    @ColumnInfo
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

    val color: Int

) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

    @ColumnInfo(name = "create_date")
    @SerializedName("create_date")
    val createDate: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val today = LocalDate.now()
        today.format(DateTimeFormatter.ofPattern("dd MMM"))
    } else {
        val date = Calendar.getInstance()
        date.toString()
    }

    constructor(jsonData: JSONObject) : this(
        jsonData.getString("firstName"),
        jsonData.getString("lastName"),
        jsonData.getString("job"),
        jsonData.getString("position"),
        jsonData.getString("email"),
        jsonData.getString("phoneMobile"),
        jsonData.getString("phoneOffice"),
        jsonData.getInt("color")
    )
}
