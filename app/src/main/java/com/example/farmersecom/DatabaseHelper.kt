package com.example.farmersecom

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.farmersecom.features.cart.data.framework.CartDao
import com.example.farmersecom.features.cart.data.framework.DateConverters
import com.example.farmersecom.features.cart.domain.CartItem
import com.example.farmersecom.utils.constants.Constants.DATABASE_VERSION

@Database(entities = [CartItem::class],exportSchema = false,version = DATABASE_VERSION )
@TypeConverters(DateConverters::class)
abstract class DatabaseHelper : RoomDatabase()
{

    abstract fun cartDao():CartDao

}