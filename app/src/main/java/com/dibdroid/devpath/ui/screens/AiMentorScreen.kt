package com.dibdroid.devpath.ui.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.dibdroid.devpath.data.model.Content
import com.dibdroid.devpath.data.model.GeminiRequest
import com.dibdroid.devpath.data.model.Part
import com.dibdroid.devpath.data.remote.GeminiApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// 1. Message structure

data class ChatMessage(val text : String, val isUser : Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiMentorScreen() {

    // 1. Create a client with longer timeouts (30 seconds or 60 seconds)
    val okHttpClient = remember {
        okhttp3.OkHttpClient.Builder()
            .connectTimeout(60,java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60,java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }




    // ----- RETROFIT SETUP (Replace GenerativeModel)---
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(okHttpClient) // after i face error time out issue then i implement this
            .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    var apiService = remember { retrofit.create(GeminiApiService::class.java) }



    val coroutineScope = rememberCoroutineScope()




// 2. state : List of message (Start with the custom greeting)

    var messages by remember {
        mutableStateOf(listOf(
            ChatMessage("Hello👋 How can I help you with your learning today?", false)
        ))
    }

    //3. State : The text user is currently typing
    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column{
                TopAppBar(
                    title = {
                        Text(text = "AI Mentor Fixed",
                            fontWeight = FontWeight.Bold ,
                            color = Color(0xFF214EF3) // Brand Blue
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)

                )
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.4f))

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            //4. CHAT AREA
            LazyColumn(
                modifier = Modifier.weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 20.dp, bottom = 20.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message)
                }

                // show "Thinking.." when AI is generating a response
                if (isLoading) {
                    item {
                        Text(
                         text = "typing....",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 8.dp)

                        )
                    }
                }


            }

            // 5. INPUT AREA (At the bottom)
            Surface(
                tonalElevation = 8.dp,
                color = Color.White,
                modifier = Modifier.imePadding() // Ensure it stays above keyboard
            ) {
                Row(
                    modifier = Modifier.padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = inputText,
                        onValueChange = {inputText = it},
                        placeholder = {Text("Type a message...", color = Color.Gray)},
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading,  // Disable typing while AI is thinking
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(28.dp)


                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    // The Round Blue Send Button
                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                val userMessage = inputText // step 1: save message
                                    messages = messages + ChatMessage(userMessage, true)
                                    // Clear input after sending
                                    inputText = ""
                                isLoading= true


                                    // --- RETROFIT CALL ---

                                    coroutineScope.launch {
                                        try {
                                            val request =
                                                GeminiRequest(
                                                    contents = listOf(
                                                        Content(parts = listOf(Part(userMessage)))
                                                    )
                                                )
                                            val response = apiService.getAiResponse(
                                                apiKey = "AIzaSyBctiGw7Q5h6AVM-6rcM5nOdAgUhQHLuQo",
                                                request = request
                                            )
                                            // We use .candidates because its plural in the JSON
                                            val aiResponse = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                                                ?: "No Response from ai"
                                            messages = messages + ChatMessage(aiResponse, false)
                                        } catch (e: Exception) {

                                            android.util.Log.e("Ai Mentor","Ai Call Failed: ${e.message}", e)
                                            messages =
                                                messages + ChatMessage(
                                                    "Error : ${e.localizedMessage}",
                                                    false
                                                )
                                        }finally {
                                            isLoading = false
                                        }
                                    }
                                }
                        },
                        modifier = Modifier.size(48.dp)
                            .background(Color(0xFF214EF3), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }


}

/**
 * COMPONENT : ChatBubble
 * Drwas AI (Blue) and User (Green) bubbles
 *
 */

@Composable
fun ChatBubble(message: ChatMessage){
    val isUser = message.isUser
    val alignment = if (isUser) Alignment.End else Alignment.Start

   // Design Colors : Light Blue for AI, Light Green for User
   val bubbleColor = if (isUser) Color(0xFF43A947).copy(alpha = 0.12f) else
       Color(0xFF214EF3).copy(alpha = 0.08f)
   val textColor = if (isUser) Color(0xFF1B5E20) else Color(0xFF0047A1)

   Column(
       modifier = Modifier.fillMaxWidth(),
       horizontalAlignment = alignment
   ) {
       Surface(
           color = bubbleColor,
           shape = RoundedCornerShape(
               topStart = 16.dp,
               topEnd =  16.dp,
               bottomStart = if (isUser) 16.dp else 0.dp, // Tail Logic
               bottomEnd = if (isUser) 0.dp else 16.dp
           )
       ) {
           Text(
               text = message.text,
               modifier = Modifier.padding(15.dp),
               fontSize = 15.sp,
               color = textColor,
               lineHeight = 22.sp
           )
       }
   }

}