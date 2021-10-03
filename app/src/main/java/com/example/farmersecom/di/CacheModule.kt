package com.example.farmersecom.di

import android.content.Context
import androidx.room.Room
import com.example.farmersecom.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule
{


//    @Provides
//    @Singleton
//    fun provideDatabaseHelper(@ApplicationContext context: Context): DatabaseHelper
//    = Room.databaseBuilder(context, DatabaseHelper::class.java,DATABASE_NAME).build()

//    @Provides
//    @Singleton
//    fun providesArticleDao(databaseHelper: DatabaseHelper): ArticleDao =
//            databaseHelper.articleDao()
//


} // cacheModule closed