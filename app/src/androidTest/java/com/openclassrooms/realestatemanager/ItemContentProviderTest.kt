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
    }

    @Test
    fun insertAndGetItem() {
        val cursor = mContentResolver!!.query(ItemContentProvider.URI_ITEM, null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(2))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), `is`("3"))
    }

}