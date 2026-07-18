package com.gesturecontacts.contacts

import android.content.Context
import android.provider.ContactsContract
import com.gesturecontacts.db.ContactDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactDao: ContactDao,
    private val context: Context
) {
    
    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts()
    }
    
    fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts(query)
    }
    
    suspend fun loadContactsFromDevice() {
        val deviceContacts = fetchDeviceContacts()
        if (deviceContacts.isNotEmpty()) {
            contactDao.insertContacts(deviceContacts)
        }
    }
    
    private fun fetchDeviceContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        ) ?: return emptyList()
        
        cursor.use { c ->
            while (c.moveToNext()) {
                val idIndex = c.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                
                if (idIndex >= 0 && nameIndex >= 0) {
                    val id = c.getString(idIndex)
                    val name = c.getString(nameIndex)
                    
                    val phone = getContactPhone(id)
                    val email = getContactEmail(id)
                    
                    contacts.add(
                        Contact(
                            id = id,
                            name = name,
                            phone = phone,
                            email = email
                        )
                    )
                }
            }
        }
        
        return contacts
    }
    
    private fun getContactPhone(contactId: String): String {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(contactId),
            null
        ) ?: return ""
        
        var phone = ""
        cursor.use { c ->
            if (c.moveToFirst()) {
                val phoneIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (phoneIndex >= 0) {
                    phone = c.getString(phoneIndex)
                }
            }
        }
        
        return phone
    }
    
    private fun getContactEmail(contactId: String): String {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(contactId),
            null
        ) ?: return ""
        
        var email = ""
        cursor.use { c ->
            if (c.moveToFirst()) {
                val emailIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                if (emailIndex >= 0) {
                    email = c.getString(emailIndex)
                }
            }
        }
        
        return email
    }
}
