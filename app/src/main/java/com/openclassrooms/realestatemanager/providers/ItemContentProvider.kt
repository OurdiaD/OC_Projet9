package com.openclassrooms.realestatemanager.providers

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.database.REMDatabase
import com.openclassrooms.realestatemanager.model.Property

class ItemContentProvider: ContentProvider() {

    companion object {
        // FOR DATA
        val AUTHORITY = "com.openclassrooms.realestatemanager.providers"
        val TABLE_NAME = Property::class.java.simpleName
        val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?,
                       selection: String?,
                       selectionArgs: Array<String>?,
                       sortOrder: String?): Cursor? {

        if (context != null) {
            val mContext = context!!
            val db = REMDatabase.getInstance(mContext)
            val cursor = db!!.propertyDao().getAllsItemsWithCursor()
            cursor.setNotificationUri(mContext.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {

        if (context != null) {
            val mContext = context!!
            val db = REMDatabase.getInstance(mContext)
            val id = db!!.propertyDao().insert(Property.fromContentValues(contentValues!!))
            if (id != 0L) {
                mContext.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
        }

        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        if (context != null) {
            val mContext = context!!
            val db = REMDatabase.getInstance(mContext)
            val count = db!!.propertyDao().delete(ContentUris.parseId(uri))
            mContext.contentResolver.notifyChange(uri, null)
            return 0
        }
        throw IllegalArgumentException("Failed to delete row into $uri")

    }

    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        if (context != null) {
            val mContext = context!!
            val db = REMDatabase.getInstance(mContext)
            val count = db!!.propertyDao().update(Property.fromContentValues(contentValues!!))
            mContext.contentResolver.notifyChange(uri, null)
            return 0
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }

}
