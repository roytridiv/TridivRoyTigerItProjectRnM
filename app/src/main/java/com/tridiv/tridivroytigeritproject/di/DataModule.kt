package com.tridiv.tridivroytigeritproject.di

import android.content.Context
import androidx.room.Room
import com.tridiv.tridivroytigerit.data.db.AppDatabase
import com.tridiv.tridivroytigerit.data.db.CharactersDataDao
import com.tridiv.tridivroytigeritproject.data.domain.common.Constants
import com.tridiv.tridivroytigeritproject.data.RepositoryImplementation
import com.tridiv.tridivroytigeritproject.data.network.RestApiService
import com.tridiv.tridivroytigeritproject.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun bindRepo(
        @ApplicationContext context: Context,
        apiService: RestApiService,
        charactersDataDao: CharactersDataDao,
        ): Repository =
        RepositoryImplementation(
            apiService,
            charactersDataDao
        )

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, Constants.DB_NAME
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun providesTvDao(appDatabase: AppDatabase) =
        appDatabase.getCharactersDao()
}