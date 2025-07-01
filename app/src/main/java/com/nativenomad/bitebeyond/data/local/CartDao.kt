package com.nativenomad.bitebeyond.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nativenomad.bitebeyond.models.CartItemEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartItemEntity)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItemEntity>

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
//
//    @Update
//    suspend fun updateItem(item: CartItemEntity)

    @Delete
    suspend fun deleteItem(item: CartItemEntity)
}