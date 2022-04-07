package com.sweeftdigital.contactsexchange.app.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table WHERE is_my == 323 ORDER BY name ASC")
    fun selectAllMyContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact_table WHERE is_my == 324 ORDER BY name ASC")
    fun selectAllScannedContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    suspend fun selectAllContacts(): List<ContactEntity>

    @Query("SELECT * FROM contact_table WHERE id == :id")
    suspend fun selectContactById(id: Int): ContactEntity

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