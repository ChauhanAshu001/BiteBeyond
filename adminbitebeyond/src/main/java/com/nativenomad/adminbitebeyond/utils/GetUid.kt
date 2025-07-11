package com.nativenomad.adminbitebeyond.utils

import com.google.firebase.auth.FirebaseAuth

object GetUid {
    fun getUid():String{
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid.toString()
        return uid
    }
}