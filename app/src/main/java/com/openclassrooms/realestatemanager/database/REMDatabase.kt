package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class REMDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao

    var instance: REMDatabase? = null

   /* open fun getInstance(context: Context): REMDatabase? {
        if (instance == null) {
            synchronized(REMDatabase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        REMDatabase::class.java, "REM_database")
                        .build()
                }
            }
        }
        return instance
    }*/
   companion object {
       private var INSTANCE: REMDatabase? = null

       fun getInstance(context: Context): REMDatabase? {
           /*if (INSTANCE == null) {
               synchronized(REMDatabase::class) {
                   INSTANCE = Room.databaseBuilder(context.applicationContext, REMDatabase::class.java, "user.db").build()
               }
           }*/
           if (INSTANCE == null) {
               synchronized(REMDatabase::class.java) {
                   if (INSTANCE == null) {
                       INSTANCE = Room.databaseBuilder(
                           context.applicationContext,
                           REMDatabase::class.java, "REM_database"
                       )
                           .build()
                   }
               }
           }
           return INSTANCE
       }

   }

}