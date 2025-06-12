package com.nativenomad.bitebeyond.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.nativenomad.bitebeyond.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalUserManagerImpl(
    private val context: Context
): LocalUserManager {

    override suspend fun saveAppEntry() {
        context.datastore.edit{it->
            it[PreferenceKey.APP_ENTRY]=true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.datastore.data.map{
            it[PreferenceKey.APP_ENTRY]?:false  //elvis operator
        }
    }

}

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "USER_SETTINGS")
private object PreferenceKey{
    val APP_ENTRY= booleanPreferencesKey(name="entered_app")
}