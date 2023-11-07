package com.example.rockpaperscissors

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = arrayOf(PlayerEntity::class), version = 1)
abstract class PlayerDatabase:RoomDatabase(){
    abstract fun playerDao():PlayerDao
}