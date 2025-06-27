package com.nativenomad.bitebeyond.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nativenomad.bitebeyond.models.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1)
abstract class CartDatabase:RoomDatabase() {
    abstract fun cartDao():CartDao
}