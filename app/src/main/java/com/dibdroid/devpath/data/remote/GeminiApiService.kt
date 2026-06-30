package com.dibdroid.devpath.data.remote

import com.dibdroid.devpath.data.model.GeminiRequest
import com.dibdroid.devpath.data.model.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    // this is called the specific v1 end point
// directly
    @POST("v1beta/models/gemini-3-flash-preview:generateContent")
    suspend fun getAiResponse(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ) : GeminiResponse

}