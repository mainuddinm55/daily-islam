package info.learncoding.dailyislam.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.learncoding.dailyislam.DailyIslamApp
import info.learncoding.dailyislam.data.local.AppDatabase
import info.learncoding.dailyislam.data.network.ApiClient
import info.learncoding.dailyislam.data.repository.HadithRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideDatabase(app: DailyIslamApp): AppDatabase {
         return Room.databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHadithRepo(apiClient: ApiClient, database: AppDatabase): HadithRepositoryImp {
        return HadithRepositoryImp(apiClient, database)
    }
}