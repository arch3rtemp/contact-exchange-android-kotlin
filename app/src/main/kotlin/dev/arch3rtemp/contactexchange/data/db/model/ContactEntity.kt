package dev.arch3rtemp.contactexchange.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    val job: String,
    val position: String,
    val email: String,
    @ColumnInfo(name = "phone_mobile")
    val phoneMobile: String,
    @ColumnInfo(name = "phone_office")
    val phoneOffice: String,
    @ColumnInfo(name = "create_date")
    val createdAt: Long,
    val color: Int,
    @ColumnInfo(name = "is_my")
    val isMy: Boolean
)
