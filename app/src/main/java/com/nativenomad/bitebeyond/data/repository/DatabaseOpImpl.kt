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
import com.nativenomad.bitebeyond.models.FoodItem
import com.nativenomad.bitebeyond.models.Offers
import com.nativenomad.bitebeyond.models.Order
import com.nativenomad.bitebeyond.models.Restaurants
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DatabaseOpImpl(private val application: Application) : DatabaseOp {

    private val database = Firebase.database("https://bitebeyond-default-rtdb.firebaseio.com/")

    override suspend fun getCategories(): Flow<List<Category>> = callbackFlow {
        val ref = database.getReference("Categories")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoryList = mutableListOf<Category>()
                for (child in snapshot.children) {
                    val category = child.getValue(Category::class.java)
                    if (category != null) {
                        categoryList.add(category)
                    }
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
                val restaurantList = mutableListOf<Restaurants>()
                for (child in snapshot.children) {
                    val restaurant = child.getValue(Restaurants::class.java)
                    if (restaurant != null) {
                        restaurantList.add(restaurant)
                    }
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

    override suspend fun getMenu(restaurantUid: String): Flow<List<FoodItem>> {
        return callbackFlow {
            val menuRef = database.getReference("Restaurants")
                .child(restaurantUid).child("menu")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        val name = it.child("name").getValue<String>() ?: return@mapNotNull null
                        val cost = it.child("cost").getValue<String>()  ?: return@mapNotNull null
                        val imageUrl = it.child("imageUrl").getValue<String>()  ?: return@mapNotNull null
                        FoodItem(name, cost, imageUrl,restaurantUid)
                    }
                    trySend(list).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            menuRef.addValueEventListener(listener)
            awaitClose { menuRef.removeEventListener(listener) }
        }


    }

    override suspend fun getOffers(restaurantUid: String): Flow<List<Offers>> {
        return callbackFlow {
            val offersRef = database.getReference("Restaurants")
                .child(restaurantUid).child("Offers")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        val offerDescription = it.child("offerDescription").getValue<String>() ?: return@mapNotNull null
                        val promoCode = it.child("promoCode").getValue<String>()  ?: return@mapNotNull null
                        Offers(offerDescription =offerDescription,promoCode=promoCode)
                    }
                    trySend(list).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            offersRef.addValueEventListener(listener)
            awaitClose { offersRef.removeEventListener(listener) }
        }
    }

    override suspend fun getPromoCodeRestaurantMap(): Flow<MutableMap<String, String>> {
        return callbackFlow {
            val restaurantsRef = database.getReference("Restaurants")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val result = mutableMapOf<String, String>()

                    for (restaurantSnap in snapshot.children) {
                        val restaurantUid = restaurantSnap.child("uid").getValue<String>().toString()
                        val offersSnap = restaurantSnap.child("Offers")

                        for (offerSnap in offersSnap.children) {
                            val promoCode = offerSnap.child("promoCode").getValue<String>()?.uppercase()
                            if (promoCode != null) {
                                result[promoCode] = restaurantUid
                            }
                        }
                    }

                    trySend(result).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            restaurantsRef.addValueEventListener(listener)
            awaitClose { restaurantsRef.removeEventListener(listener) }
        }
    }

    override suspend fun saveUserData(userId: String, user: UserProfile) {
        val ref = database.getReference("Users").child(userId)
        ref.setValue(user)
    }

    override suspend fun getUserData(userId: String): Flow<UserProfile?> {
        return callbackFlow {
            val ref = database.getReference("Users").child(userId)

            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profile = snapshot.getValue(UserProfile::class.java)
                    trySend(profile).isSuccess
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            ref.addValueEventListener(listener)
            awaitClose { ref.removeEventListener(listener) }
        }

    }

    override suspend fun getNewOrderIdForUser(userId: String): String {
        val ref = database.getReference("Orders").child(userId).push()
        return ref.key ?: System.currentTimeMillis().toString()
    }

    override suspend fun saveOrderToUserNode(userId: String, orderId: String, order: Order) {
        database.getReference("Orders")
            .child(userId)
            .child(orderId)
            .setValue(order).await()
    }

    override suspend fun saveOrderToAdminNode(restaurantId: String, orderId: String, order: Order) {
        database
            .getReference("AdminOrders")
            .child(restaurantId)
            .child(orderId)
            .setValue(order).await()
    }
}
