package fr.leboncoin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.leboncoin.data.source.local.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM albums")
    fun getAlbumsFlow(): Flow<List<AlbumEntity>>

    @Query("SELECT * FROM albums")
    suspend fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Int): AlbumEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: AlbumEntity)

    @Query("DELETE FROM albums WHERE id = :id")
    suspend fun deleteAlbum(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<AlbumEntity>)

    @Query("DELETE FROM albums")
    suspend fun clearAlbums()
}
