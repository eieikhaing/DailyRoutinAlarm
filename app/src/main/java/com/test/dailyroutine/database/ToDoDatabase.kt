package com.test.dailyroutine.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoTaskModel::class],version = 1,exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val todoDao : ToDoDao
    companion object{

        @Volatile
        private var INSTANCE : ToDoDatabase? = null

        fun getInstance(context : Context) : ToDoDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoDatabase::class.java,
                        "to_do_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}