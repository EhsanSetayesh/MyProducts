package co.ehsansetayesh.data_local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import co.ehsansetayesh.data_local.db.product.ProductDao
import co.ehsansetayesh.data_local.db.product.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}