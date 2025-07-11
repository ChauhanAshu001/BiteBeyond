package com.nativenomad.adminbitebeyond.data.repository


import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.nativenomad.adminbitebeyond.domain.repository.DatabaseOp
import com.nativenomad.adminbitebeyond.models.Menu
import com.nativenomad.adminbitebeyond.models.Offers
import com.nativenomad.adminbitebeyond.models.RestaurantEntity
import com.nativenomad.adminbitebeyond.utils.GetUid.getUid
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class DatabaseOpImpl():DatabaseOp {
    private val database = Firebase.database("https://bitebeyond-default-rtdb.firebaseio.com/")


    override suspend fun saveRestaurantData(restaurantEntity: RestaurantEntity) {
        val uid=getUid()
        val ref = database.getReference("Restaurants").child(uid)

        //map ensures that only these fields are updated and else entire object under uid is updated and Offers node is deleted because Restaurant Entity don't have Offers node
        val updateMap = mapOf(
            "restaurantName" to restaurantEntity.restaurantName,
            "imageUrl" to restaurantEntity.imageUrl,
            "restaurantDescription" to restaurantEntity.restaurantDescription,
            "address" to restaurantEntity.address,
            "pincode" to restaurantEntity.pincode,
            "state" to restaurantEntity.state,
            "country" to restaurantEntity.country,
            "latitude" to restaurantEntity.latitude,
            "longitude" to restaurantEntity.longitude
        )

        ref.updateChildren(updateMap).await()  //.await() sends error if operation failed else returns no error, this will be used in UI's viewmodel to change value of _uiState
    }


    override suspend fun addMenuItem(menuItem: Menu) {
        val uid=getUid()
        val menuRef = database.getReference("Restaurants").child(uid).child("menu")

        val snapshot = menuRef.get().await() // this throws on failure
        val nextIndex = snapshot.childrenCount.toInt()

        menuRef.child(nextIndex.toString()).setValue(menuItem).await() // also throws on failure
    }
    override suspend fun saveCategoriesGlobally(menuItem: Menu){
        val categoriesRef = database.getReference("Categories")
        val capitalizedCategory = menuItem.foodCategory.trim().uppercase()

        // Checking if this category already exists
        val snapshot = categoriesRef.get().await()
        val exists = snapshot.children.any { child ->
            val existingCategory = child.child("foodCategory").getValue(String::class.java)
            existingCategory?.trim()?.uppercase() == capitalizedCategory
        }

        if (!exists) {
            // Adding new global category if it don't exist
            val nextIndex = snapshot.childrenCount.toInt()

            val categoryData = mapOf(
                "imageUrl" to menuItem.imageUrl,
                "foodCategory" to capitalizedCategory
            )

            categoriesRef.child(nextIndex.toString()).setValue(categoryData).await()
        }
    }

    override suspend fun getFullMenu():Flow<List<Menu>>{
        val uid= getUid()
        return callbackFlow {
            val menuRef = database.getReference("Restaurants")
                .child(uid).child("menu")
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val menuList = mutableListOf<Menu>()
                    for(child in snapshot.children){
                        val menu=child.getValue(Menu::class.java)
                        menuList.add(menu!!)
                    }
                    trySend(menuList)
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            menuRef.addValueEventListener(listener)
            awaitClose { menuRef.removeEventListener(listener) }
        }
    }


    override suspend fun removeMenuItem(menuItem: Menu) {
        val uid=getUid() //don't make it global to class because class is initialised by hilt as soon as app is opened but at that time user may not be logged in hence uid=null
        val ref = database.getReference("Restaurants").child(uid).child("menu")
        val snapshot = ref.get().await()

        for (child in snapshot.children) {
            val item = child.getValue(Menu::class.java)
            if (item?.name == menuItem.name &&
                item.cost == menuItem.cost &&
                item.imageUrl == menuItem.imageUrl
            ) {
                child.ref.removeValue().await() // throws if fails
                break
            }
        }
    }

    override suspend fun getMyOffers(): Flow<List<Offers>> {
        val uid= getUid()
        return callbackFlow {
            val offersRef = database.getReference("Restaurants")
                .child(uid).child("Offers")
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

    override suspend fun getAllOffers(): Flow<List<Offers>> = callbackFlow {
        val ref = database.getReference("AvailablePromoCodes")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val offersList = mutableListOf<Offers>()
                for (child in snapshot.children) {
                    val promoCode = child.child("code").getValue(String::class.java)
                    val description = child.child("description").getValue(String::class.java)

                    if (!promoCode.isNullOrBlank() && !description.isNullOrBlank()) {
                        offersList.add(Offers(offerDescription = description, promoCode = promoCode))
                    }
                }
                trySend(offersList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error fetching promo codes", error.toException())
                close(error.toException())
            }
        }

        ref.addValueEventListener(listener)
        awaitClose { ref.removeEventListener(listener) }
    }


    override suspend fun addOffer(offer: Offers) {
        val uid = getUid()
        val ref = database.getReference("Restaurants").child(uid).child("Offers")

        // Getting current number of offers to determine the next index
        val snapshot = ref.get().await()
        val nextIndex = snapshot.childrenCount.toInt()

        //setting the data class
        ref.child(nextIndex.toString()).setValue(offer).await()
    }

    override suspend fun deleteOffer(offer: Offers) {
        val uid = getUid()
        val ref = database.getReference("Restaurants").child(uid).child("Offers")

        // Fetch all offers to find the matching one
        val snapshot = ref.get().await()
        for (child in snapshot.children) {
            val existingOffer = child.getValue(Offers::class.java)
            if (existingOffer?.promoCode == offer.promoCode &&
                existingOffer.offerDescription == offer.offerDescription
            ) {
                // Match found, delete this node
                ref.child(child.key!!).removeValue().await()  //child.key return 0,1,2... index string under which offer is stored
                break
            }
        }
    }


}