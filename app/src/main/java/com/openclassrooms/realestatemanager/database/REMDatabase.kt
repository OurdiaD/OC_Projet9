package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Property

import androidx.sqlite.db.SupportSQLiteDatabase

import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.PointsOfInterest
import java.util.concurrent.Executors


@Database(entities = [Property::class, Picture::class], version = 1, exportSchema = false)
abstract class REMDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
    abstract fun pictureDao(): PictureDao

   companion object {
       private var INSTANCE: REMDatabase? = null

       fun getInstance(context: Context): REMDatabase? {
           if (INSTANCE == null) {
               synchronized(REMDatabase::class.java) {
                   if (INSTANCE == null) {
                       INSTANCE = Room.databaseBuilder(
                           context.applicationContext,
                           REMDatabase::class.java, "REM_database"
                       )
                           .addCallback(sRoomDatabaseCallback)
                           .build()
                   }
               }
           }
           return INSTANCE
       }

       private val sRoomDatabaseCallback: Callback = object : Callback() {
           override fun onCreate(db: SupportSQLiteDatabase) {
               Executors.newSingleThreadExecutor().execute {
                   INSTANCE?.propertyDao()?.insertAll(populateData())
               }
           }
       }

       fun populateData(): List<Property> {
           val address1 = Address("5", "Rue Xaintrailles", "Orléan", "45000")
           val address2 = Address("22", "rue de l'eglise", "Montpellier", "34080")

           val property1= Property(3, 7500, 100)
           property1.status = 0
           property1.numberOfRooms = 3
           property1.describe = "description du bien immo"
           property1.address = address1
           property1.agent = "nom de l'agent"
           property1.pointsOfInterest = PointsOfInterest(school = false, market = true, park = true)

           val property2 = Property(1, 12000, 25)
           property2.status = 0
           property2.numberOfRooms = 2
           property2.describe = "description du bien immo2"
           property2.address = address2
           property2.agent = "nom de l'agent2"
           property2.pointsOfInterest = PointsOfInterest(school = true, market = true, park = false)

           return listOf(property1,property2)
       }

   }

}