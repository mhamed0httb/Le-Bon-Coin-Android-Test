package fr.leboncoin.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.leboncoin.data.source.local.dao.AlbumDao
import fr.leboncoin.data.source.local.dao.FavoriteAlbumDao
import fr.leboncoin.data.source.local.entity.AlbumEntity
import fr.leboncoin.data.source.local.entity.FavoriteAlbumEntity

@Database(
    entities = [AlbumEntity::class, FavoriteAlbumEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun favoriteAlbumDao(): FavoriteAlbumDao

    companion object {
        const val DATABASE_NAME = "leboncoin_db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example: database.execSQL("ALTER TABLE albums ADD COLUMN description FAVORITE")
            }
        }
    }
}
