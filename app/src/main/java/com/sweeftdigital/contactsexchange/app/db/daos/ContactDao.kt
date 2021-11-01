package com.sweeftdigital.contactsexchange.app.db.daos

import androidx.room.*
import com.sweeftdigital.contactsexchange.app.db.models.ContactEntity
import io.reactivex.Flowable

@Dao
interface ContactDao {
    @Query("SELECT * FROM contact_table WHERE is_my == 1 ORDER BY last_name ASC")
    fun selectAllMyContacts(): Flowable<List<ContactEntity>>

    @Query("SELECT * FROM contact_table WHERE is_my == 0 ORDER BY last_name ASC")
    fun selectAllScannedContacts(): Flowable<List<ContactEntity>>

    @Query("SELECT * FROM contact_table WHERE id == :id")
    fun selectContactById(id: Int): Flowable<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contactEntities: List<ContactEntity>)

    @Update
    fun update(contactEntity: ContactEntity)

    @Query("DELETE FROM contact_table WHERE id == :id")
    fun delete(id: Int)

    @Query("DELETE FROM contact_table")
    fun deleteAll()
}