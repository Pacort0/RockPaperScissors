package com.example.rockpaperscissors

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Jugadores")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var username:String = "",
    var highScore:Int = 0
)
