package com.example.rockpaperscissors

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {
    @Query("SELECT * FROM jugadores")
    suspend fun selectAllPlayers():MutableList<PlayerEntity>
    @Query("SELECT * FROM jugadores WHERE id = :id")
    suspend fun findById(id:Long):PlayerEntity
    @Insert
    suspend fun addPlayer(playerEntity: PlayerEntity):Long
}
