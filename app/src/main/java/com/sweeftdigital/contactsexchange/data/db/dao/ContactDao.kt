package com.sweeftdigital.contactsexchange.data.db.dao

import androidx.room.*
import com.sweeftdigital.contactsexchange.data.db.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table WHERE is_my == 0 ORDER BY name ASC")
    fun selectAllMyContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact_table WHERE is_my == 1 ORDER BY name ASC")
    fun selectAllScannedContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    suspend fun selectAllContacts(): List<ContactEntity>

    @Query("SELECT * FROM contact_table WHERE id == :id")
    suspend fun selectContactById(id: Int): ContactEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contactEntity: ContactEntity)

    @Update
    suspend fun update(contactEntity: ContactEntity)

    @Query("DELETE FROM contact_table WHERE id == :id")
    suspend fun delete(id: Int)
}