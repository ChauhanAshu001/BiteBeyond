package com.nativenomad.bitebeyond.data.manager

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewmodel.compose.viewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.nativenomad.bitebeyond.domain.manager.AuthManager
import com.nativenomad.bitebeyond.models.AuthResponse
import com.nativenomad.bitebeyond.utils.Constants.google_web_client_id
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
                .setServerClientId(google_web_client_id) //Uses the OAuth 2.0 client ID stored in string.xml for authentication.
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

    override fun signInWithFacebook(activity: ComponentActivity, callbackManager: CallbackManager): Flow<AuthResponse> {
        return callbackFlow {
            val loginManager = LoginManager.getInstance()

            try {

                loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        result.accessToken.let { accessToken ->
                            val firebaseCredential = FacebookAuthProvider.getCredential(accessToken.token)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        trySend(AuthResponse.Success)
                                    } else {
                                        trySend(
                                            AuthResponse.Error(
                                                message = task.exception?.message ?: "Firebase sign-in failed"
                                            )
                                        )
                                    }
                                }
                        }
                    }

                    override fun onCancel() {
                        trySend(AuthResponse.Error("Facebook Sign-In Cancelled"))
                    }

                    override fun onError(error: FacebookException) {
                        trySend(AuthResponse.Error(error.message ?: "Facebook Error"))
                    }
                })

                loginManager.logInWithReadPermissions(activity, callbackManager,listOf("public_profile", "email"))
            } catch (e: Exception) {
                trySend(AuthResponse.Error(e.message ?: "Facebook Auth Exception"))
            }

            awaitClose()  // Ensures cleanup when no longer needed
        }
//        LoginManager.getInstance().registerCallback(callbackManager,
//            object:FacebookCallback<LoginResult>{
//                override fun onSuccess(loginResult: LoginResult) {
//                    TODO("Not yet implemented")
//                }
//                override fun onCancel() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(exception: FacebookException) {
//                    trySend(AuthResponse.Error(exception.message ?: "Facebook Error"))
//                }
//            })
//        LoginManager.getInstance().logInWithReadPermissions(
//            activity,
//            callbackManager,
//            listOf("public_profile", "email"),
//        )
    }

}