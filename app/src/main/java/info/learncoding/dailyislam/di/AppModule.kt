package info.learncoding.dailyislam.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.learncoding.dailyislam.DailyIslamApp
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApp(@ApplicationContext app: Context): DailyIslamApp {
        return app as DailyIslamApp
    }
}