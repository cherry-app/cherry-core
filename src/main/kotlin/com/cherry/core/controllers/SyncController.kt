package com.cherry.core.controllers

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.cherry.core.Cherry
import com.cherry.core.data.repositories.CoreDataRepository
import com.cherry.core.models.Participant
import com.cherry.core.models.persistence.RecipientType
import com.cherry.core.utilities.toJsonArray
import com.cherry.core.utilities.useAs
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser


/**
 * Created by girish on 11/12/17.
 */

class SyncController {

    class ContactInfo(val contactId: Long, val name: String, val number: String)

    fun syncContacts(context: Context) {
        val token = Cherry.Session.sessionToken ?: throw IllegalStateException("User auth token not present")
        val uid = Cherry.Session.uid ?: throw IllegalStateException("UID not present")
        val existing = CoreDataRepository.getLocalDataRepository(context).getParticipantDataStore().getParticipantUids()
        val allContactInfos = getAllPhoneNumbers(context)
        val phoneNumbers = allContactInfos.filter { !existing.contains(it.number) }.map { it.number } .toJsonArray()
        val body = JsonObject().apply { add("numbers", phoneNumbers) }
        val result = CoreDataRepository.getNetworkDataRepository().syncContacts(Cherry.partnerId, uid, token, body).execute().body()
        Log.d("Cherry", result)
        val unsyncedNumbers = (JsonParser().parse(result) as JsonArray).toList()
        unsyncedNumbers.forEach { element ->
            val number = element.asString
            if (number != Cherry.Session.uid) {
                val contactInfo = allContactInfos.getNameForNumber(number)
                val participant = Participant(number, contactInfo?.contactId ?: -1, contactInfo?.name ?: number, false, "", RecipientType.INDIVIDUAL)
                Log.d("Cherry", "Added participant: " + participant)
                CoreDataRepository.getLocalDataRepository(context).getParticipantDataStore().insertParticipant(participant)
            }
        }
    }

    fun syncGroups() {
        // TODO Implement
    }

    private fun List<ContactInfo>.getNameForNumber(phoneNumber: String): ContactInfo? =
            this.firstOrNull { it.number == phoneNumber }

    private fun getAllPhoneNumbers(context: Context): List<ContactInfo> {
        val allPhoneNumbers = ArrayList<ContactInfo>()
        val cr = context.contentResolver
        cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null).useAs {
            if (moveToFirst()) {
                do {
                    val id = getString(getColumnIndex(ContactsContract.Contacts._ID))

                    if (Integer.parseInt(getString(getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf<String>(id), null).useAs {
                            while (moveToNext()) {
                                val id = getLong(getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                                val contactNumber = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                val name = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                allPhoneNumbers.add(ContactInfo(id, name, contactNumber))
                                break
                            }
                        }
                    }

                } while (moveToNext())
            }
        }
        return allPhoneNumbers
    }
}