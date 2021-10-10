package com.example.farmersecom.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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