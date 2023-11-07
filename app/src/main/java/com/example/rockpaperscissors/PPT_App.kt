package com.example.rockpaperscissors

import android.app.Application
import androidx.room.Room

class PPT_App: Application() {
    companion object{
        lateinit var database: PlayerDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, PlayerDatabase::class.java, "ppt-db").build()
    }
}