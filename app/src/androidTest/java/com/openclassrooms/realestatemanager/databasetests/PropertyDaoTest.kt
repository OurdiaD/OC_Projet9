package com.openclassrooms.realestatemanager.databasetests

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery

import org.junit.After

import androidx.room.Room

import androidx.test.core.app.ApplicationProvider
import com.openclassrooms.realestatemanager.database.PropertyDao
import com.openclassrooms.realestatemanager.database.REMDatabase

import org.junit.Before

import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.openclassrooms.realestatemanager.model.Property
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test


class PropertyDaoTest {
    private lateinit var db: REMDatabase
    private lateinit var PropertyDao: PropertyDao

    @get:Rule
    var InstantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, REMDatabase::class.java).build()
        //db.projectDao().insertAll(Project.populateData())
        PropertyDao = db.propertyDao()
    }


    @After
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAllAndGet() {
        PropertyDao.insertAll(REMDatabase.populateData())
        val Properties: List<Property> = LiveDataTestUtil.getValue(PropertyDao.getAll())
        assertEquals(2, Properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertOne() {
        PropertyDao.insert(REMDatabase.populateData().get(1))
        val Propertys: List<Property> = LiveDataTestUtil.getValue(PropertyDao.getAll())
        assertEquals(1, Propertys.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteProperty() {
        PropertyDao.insertAll(REMDatabase.populateData())
        var Properties: List<Property> = LiveDataTestUtil.getValue(PropertyDao.getAll())
        PropertyDao.delete(Properties[1].idProperty)
        Properties = LiveDataTestUtil.getValue(PropertyDao.getAll())
        assertEquals(1, Properties.size)
    }

    @Test
    @Throws(InterruptedException::class)
    fun deleteAll() {
        PropertyDao.insertAll(REMDatabase.populateData())
        PropertyDao.deleteAll()
        val Propertys: List<Property> = LiveDataTestUtil.getValue(PropertyDao.getAll())
        assertTrue(Propertys.isEmpty())
    }

}