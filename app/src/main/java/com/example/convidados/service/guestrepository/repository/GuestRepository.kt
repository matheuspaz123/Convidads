package com.example.convidados.service.guestrepository.repository

import android.content.ContentValues
import android.content.Context
import com.example.convidados.service.guestrepository.constants.DataBaseConstants
import com.example.convidados.service.guestrepository.model.GuestModel
import java.lang.Exception
import java.util.ArrayList

class GuestRepository private constructor(context: Context) {

    private var mGuestDaraHelper: GuestDataBaseHelper = GuestDataBaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository
        fun getInstance(context: Context): GuestRepository {

            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }

            return repository
        }
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null

        try {
            val db = mGuestDaraHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                val name =
                    cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                val presence =
                    (cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                        .toInt() == 1)
                guest = GuestModel(id, name, presence)
                return guest
            }
            cursor?.close()
        } catch (e: Exception) {

        }
        return guest
    }

    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        try {
            val db = mGuestDaraHelper.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                            .toInt() == 1)
                    val guest = GuestModel(id, name, presence)

                    list.add(guest)
                }

            }
            cursor?.close()
            list
        } catch (e: Exception) {

            list
        }
        return list
    }

    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        try {
            val db = mGuestDaraHelper.readableDatabase

            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                            .toInt() == 1)
                    val guest = GuestModel(id, name, presence)

                    list.add(guest)
                }

            }
            cursor?.close()
            list
        } catch (e: Exception) {

            list
        }
        return list
    }

    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()
        try {
            val db = mGuestDaraHelper.readableDatabase

            val cursor =
                db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {

                    val id =
                        cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence =
                        (cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))
                            .toInt() == 1)
                    val guest = GuestModel(id, name, presence)

                    list.add(guest)
                }

            }
            cursor?.close()
            list
        } catch (e: Exception) {

            list
        }
        return list
    }

    fun save(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDaraHelper.writableDatabase
            val contentValue = ContentValues()
            contentValue.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValue.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValue)
            true

        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        try {
            val db = mGuestDaraHelper.writableDatabase
            val contentValue = ContentValues()
            contentValue.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValue.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.presence)
            val seletion = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())
            db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValue, seletion, args)
            return true

        } catch (e: Exception) {
            return false
        }
    }

    fun delete(id: Int): Boolean {
        try {
            val db = mGuestDaraHelper.writableDatabase
            val contentValue = ContentValues()
            val seletion = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            db.delete(DataBaseConstants.GUEST.TABLE_NAME, seletion, args)
            return true

        } catch (e: Exception) {
            return false
        }
    }
}