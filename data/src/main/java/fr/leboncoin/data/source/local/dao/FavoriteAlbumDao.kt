package fr.leboncoin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import fr.leboncoin.data.source.local.entity.FavoriteAlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAlbumDao {

    @Transaction
    suspend fun toggleFavorite(albumId: Int) {
        if (isFavorite(albumId)) {
            deleteFavorite(albumId)
        } else {
            insertFavorite(FavoriteAlbumEntity(albumId))
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteAlbumEntity)

    @Query("DELETE FROM album_favorites WHERE albumId = :albumId")
    suspend fun deleteFavorite(albumId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM album_favorites WHERE albumId = :albumId)")
    suspend fun isFavorite(albumId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM album_favorites WHERE albumId = :albumId)")
    fun observeIsFavorite(albumId: Int): Flow<Boolean>

    @Query("SELECT * FROM album_favorites")
    fun getAllFavorites(): Flow<List<FavoriteAlbumEntity>>
}