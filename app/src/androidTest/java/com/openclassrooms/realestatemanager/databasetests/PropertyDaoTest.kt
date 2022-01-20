package com.openclassrooms.realestatemanager.databasetests

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.database.REMDatabase
import com.openclassrooms.realestatemanager.model.Property
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PropertyDaoTest {
    private lateinit var db: REMDatabase
    private lateinit var propertyDao: PropertyDao

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, REMDatabase::class.java).build()
        propertyDao = db.propertyDao()
    }


    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAllAndGet() {
        propertyDao.insertAll(REMDatabase.populateData())
        val properties: List<Property> = LiveDataTestUtil.getValue(propertyDao.getAll())
        assertEquals(2, properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertOne() {
        propertyDao.insert(REMDatabase.populateData()[1])
        val properties: List<Property> = LiveDataTestUtil.getValue(propertyDao.getAll())
        assertEquals(1, properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteProperty() {
        propertyDao.insertAll(REMDatabase.populateData())
        var properties: List<Property> = LiveDataTestUtil.getValue(propertyDao.getAll())
        propertyDao.delete(properties[1].idProperty)
        properties = LiveDataTestUtil.getValue(propertyDao.getAll())
        assertEquals(1, properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteAll() {
        propertyDao.insertAll(REMDatabase.populateData())
        propertyDao.deleteAll()
        val properties: List<Property> = LiveDataTestUtil.getValue(propertyDao.getAll())
        assertTrue(properties.isEmpty())
    }

}