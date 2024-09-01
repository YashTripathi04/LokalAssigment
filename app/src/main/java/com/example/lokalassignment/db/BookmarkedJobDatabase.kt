package com.example.lokalassignment.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lokalassignment.model.BookmarkedJob

@Database(entities = [BookmarkedJob::class], version = 1)
abstract class BookmarkedJobDatabase: RoomDatabase() {

    abstract fun bookmarkedJobDao(): BookmarkedJobDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkedJobDatabase? = null

        fun getDatabase(context: Context): BookmarkedJobDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkedJobDatabase::class.java,
                    "bookmarkedJobDB"
                ).build()
                INSTANCE!!
            }
        }
    }
}