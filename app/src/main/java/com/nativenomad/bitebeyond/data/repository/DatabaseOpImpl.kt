package com.nativenomad.bitebeyond.data.repository

import android.app.Application
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.nativenomad.bitebeyond.domain.repository.DatabaseOp
import com.nativenomad.bitebeyond.models.Category
import com.nativenomad.bitebeyond.models.Restaurants
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DatabaseOpImpl(private val application: Application) : DatabaseOp {

    private val database = Firebase.database("https://bitebeyond-default-rtdb.firebaseio.com/")

    override suspend fun getCategories(): Flow<List<Category>> = callbackFlow {
        val ref = database.getReference("Categories")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoryList = snapshot.children.mapNotNull {
                    it.getValue(Category::class.java)
                }
                trySend(categoryList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching categories", error.toException())
                close(error.toException()) // optional: close the flow on error
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }

    override suspend fun getRestaurants(): Flow<List<Restaurants>> = callbackFlow {
        val ref = database.getReference("Restaurants")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val restaurantList = snapshot.children.mapNotNull {
                    it.getValue(Restaurants::class.java)
                }
                trySend(restaurantList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching restaurants", error.toException())
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }
}
