package com.openclassrooms.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.*
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.openclassrooms.realestatemanager.database.REMDatabase
import com.openclassrooms.realestatemanager.providers.ItemContentProvider
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemContentProviderTest {

    private lateinit var db: REMDatabase
    val context: Context = ApplicationProvider.getApplicationContext()

    // FOR DATA
    private var mContentResolver: ContentResolver? = null
    
    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(context, REMDatabase::class.java)
        .allowMainThreadQueries()
        .build()
        mContentResolver = context.contentResolver
        //db.propertyDao().deleteAll()
    }

    @After
    fun delete() {
        db.close()
    }
    
    @Test
    fun getItemsWhenNoItemInserted() {
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(ItemContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
        cursor.close()
    }
    
    @Test
    fun insertAndGetItem() {
        // BEFORE : Adding demo item
        val userUri = mContentResolver!!.insert(ItemContentProvider.URI_ITEM, generateItem())
        // TEST
        val cursor = mContentResolver!!.query(ContentUris.withAppendedId(ItemContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), `is`("0"))
        db.propertyDao().deleteAll()
    }

    /*@Test
    fun deleteTest() {
        var cursor = mContentResolver!!.query(ContentUris.withAppendedId(ItemContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        mContentResolver!!.delete(ContentUris.withAppendedId(ItemContentProvider.URI_ITEM, USER_ID), null, null)
        cursor = mContentResolver!!.query(ContentUris.withAppendedId(ItemContentProvider.URI_ITEM, USER_ID), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(0))
    }*/

    private fun generateItem(): ContentValues {
         val values = ContentValues()
         values.put("type", "0")
         values.put("price", "120000")
         values.put("surfaceArea", "120")
         values.put("numberOfRooms", "6")
         return values
    }
    
    companion object {
        private const val USER_ID: Long = 3
    }
}