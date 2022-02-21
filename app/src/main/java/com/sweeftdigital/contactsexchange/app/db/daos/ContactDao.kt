package com.sweeftdigital.contactsexchange.app.db.daos

import androidx.room.*
import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table WHERE is_my == 1 ORDER BY name ASC")
    fun selectAllMyContacts(): List<ContactEntity>

    @Query("SELECT * FROM contact_table WHERE is_my == 0 ORDER BY name ASC")
    fun selectAllScannedContacts(): List<ContactEntity>

    @Query("SELECT * FROM contact_table WHERE id == :id")
    fun selectContactById(id: Int): ContactEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contactEntities: List<ContactEntity>)

    @Update
    suspend fun update(contactEntity: ContactEntity)

    @Query("DELETE FROM contact_table WHERE id == :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM contact_table")
    suspend fun deleteAll()
}