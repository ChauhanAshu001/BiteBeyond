package com.nativenomad.bitebeyond.data.manager

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.models.AuthResponse
import com.nativenomad.bitebeyond.utils.Constants.web_client_id
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID

class AuthManagerImpl(val context : Context):AuthManager {
    private val auth = Firebase.auth


    override fun createAccountWithEmail(email: String, password: String): Flow<AuthResponse> {
        return callbackFlow {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    }
                }
            awaitClose()
        }
    }

    override fun loginWithEmail(email: String, password: String): Flow<AuthResponse> {
        return callbackFlow {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(AuthResponse.Success)
                    } else {
                        trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    }
                }
            awaitClose()
        }
    }

    private fun createNonce():String{ //nonce means number used once. Read about it a bit, it's interesting.
        val rawNonce= UUID.randomUUID().toString()
        val bytes=rawNonce.toByteArray()
        val md= MessageDigest.getInstance("SHA-256")
        val digest=md.digest(bytes)
        return digest.fold("") { str, it ->
            str + "%02x".format(it) }
    }

    override fun signInWithGoogle(): Flow<AuthResponse> {
        return callbackFlow {
            val googleIdOption=GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)  //Allows any account, not just pre-authorized ones.
                .setServerClientId(web_client_id) //Uses the OAuth 2.0 client ID stored in string.xml for authentication.
                .setAutoSelectEnabled(false) //Disables automatic account selection.
                .setNonce(createNonce())  //Generates a unique nonce for security
                .build()


            //Wraps the Google Sign-In options into a credential request for authentication.
            val request=GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            try{
                val credentialManager=CredentialManager.create(context)
                val result=credentialManager.getCredential(
                    context=context,
                    request=request)
                val credential=result.credential
                if(credential is CustomCredential){
                    if (credential.type==GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                        try{
                            val googleIdTokenCredential=GoogleIdTokenCredential
                                .createFrom(credential.data)
                            val firebaseCredential=GoogleAuthProvider
                                .getCredential(
                                    googleIdTokenCredential.idToken,
                                    null
                                )
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        //here you can take user's data from google and save it in database for showing in profile section.
                                        trySend(AuthResponse.Success)
                                    }
                                    else{
                                        trySend(AuthResponse.Error(message=it.exception?.message?:""))
                                    }
                                }
                        }
                        catch (e:GoogleIdTokenParsingException){
                            trySend(AuthResponse.Error(message=e.message?:""))
                        }
                    }
                }

            }catch (e:Exception){
                trySend(AuthResponse.Error(message=e.message?:""))
            }
            awaitClose() //Ensures the flow properly cleans up when no longer needed.
        }
    }

}