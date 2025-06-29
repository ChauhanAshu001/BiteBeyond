package com.nativenomad.bitebeyond.remote

import retrofit2.Response
import com.nativenomad.bitebeyond.remote.dto.UploadResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FreeImageApi {
    @FormUrlEncoded
    @POST("api/1/upload")  /*post method is used because on website it was given "Always use POST when uploading local files"*/
    suspend fun uploadImage(
        @Field("key") apiKey: String="6d207e02198a847aa98d0a2a901485a5",   //with Post method we use @Field instead of @Query
//        @Field("action") action: String = "upload",
        @Field("source") base64Image: String,
        @Field("format") format: String = "json"
    ): Response<UploadResponse>
}
