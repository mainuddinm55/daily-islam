package info.learncoding.dailyislam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import info.learncoding.dailyislam.data.local.AppDatabase.Companion.DATABASE_VERSION
import info.learncoding.dailyislam.data.local.converter.HadithBookTypeConverter
import info.learncoding.dailyislam.data.local.converter.HadithCollectionTypeConverter
import info.learncoding.dailyislam.data.local.converter.HadithTypeConverter
import info.learncoding.dailyislam.data.local.dao.HadithBookDao
import info.learncoding.dailyislam.data.local.dao.HadithCollectionDao
import info.learncoding.dailyislam.data.local.dao.HadithDao
import info.learncoding.dailyislam.data.model.Hadith
import info.learncoding.dailyislam.data.model.HadithBook
import info.learncoding.dailyislam.data.model.HadithCollection

@Database(
    entities = [Hadith::class, HadithBook::class, HadithCollection::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    HadithTypeConverter::class,
    HadithBookTypeConverter::class,
    HadithCollectionTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hadithCollectionDao(): HadithCollectionDao
    abstract fun hadithBookDao(): HadithBookDao
    abstract fun hadithDao(): HadithDao

    companion object {
        const val DATABASE_NAME = "daily_islam"
        const val DATABASE_VERSION = 5
    }
}