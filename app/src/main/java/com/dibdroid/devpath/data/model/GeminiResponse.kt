package com.dibdroid.devpath.data.model


data class GeminiRequest(val contents : List<Content>)
data class Content(val parts: List<Part>)
data class Part(val text : String)

class GeminiResponse (
    val candidates : List<Candidate>? = null ,
    val error : GeminiError? = null // To catch the error

    )

data class Candidate(val content: Content)
data class GeminiError(val message : String, val code : Int)