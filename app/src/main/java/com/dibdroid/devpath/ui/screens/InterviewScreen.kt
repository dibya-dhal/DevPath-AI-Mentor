package com.dibdroid.devpath.ui.screens
import android.R
import android.widget.Space
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dibdroid.devpath.data.model.Content
import com.dibdroid.devpath.data.model.GeminiRequest
import com.dibdroid.devpath.data.model.Part
import com.dibdroid.devpath.data.remote.GeminiApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewScreen(){
    // 1. Setup Retrofit (Ideally move to a viewmodel later)
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService = remember { retrofit.create(GeminiApiService::class.java) }
    val coroutineScope = rememberCoroutineScope()

    // 2. State
    var currentQuestion = "What is the difference between List and Set in Java?"
    var userAnswer by remember { mutableStateOf("") }
    var aiFeedback by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Interview Prep", fontWeight = FontWeight.Bold, color = Color(0xFF214EF3))},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
           // QUESTION CARD
            Card(modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF214EF3).copy(alpha = 0.05f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Question", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(currentQuestion, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                }

            }

            Spacer(modifier = Modifier.height(24.dp))

            // ANSWER INPUT HERE
            Text("Your Answer", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it},
                modifier = Modifier.fillMaxWidth().height(150.dp),
               placeholder = {Text("Type your answer here...") },
               shape = RoundedCornerShape(12.dp)
            )
           Spacer(modifier = Modifier.height(24.dp))

           // ACTION BUTTON

            Button(
                onClick = {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val prompt = "Question: $currentQuestion\n" +
                                    "Answer: $userAnswer\n" +
                                    "Act as an Android interviewer for a JUNIOR position. " +
                                    "1. Grade this answer out of 10. " +
                                    "2. Give 3 short tips for a beginner. " +
                                    "3. Provide one new BEGINNER-LEVEL Android question (focused on Kotlin, Activity lifecycle, or basic UI). " +
                                    "Keep the next question simple. Format it as 'Next Question: [Question]'"
                            val request = GeminiRequest(contents = listOf(Content(parts = listOf(
                                Part(prompt)))))
                            val response = apiService.getAiResponse("AIzaSyBctiGw7Q5h6AVM-6rcM5nOdAgUhQHLuQo",request)
                            aiFeedback = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No feedback"
                        } catch (e : Exception) {
                            aiFeedback = "Error: ${e.localizedMessage}"

                        }finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = userAnswer.isNotBlank() && !isLoading,
                shape = RoundedCornerShape(12.dp)


            ) {

                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))

                }else {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Get AI Feedback")

                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // FEEDBACK CARD (Conditional)
            if (aiFeedback.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF43A947).copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF43A947).copy(alpha = 0.5f))
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF43A947))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("AI Feedback ", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))

                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(aiFeedback, fontSize = 14.sp, color = Color.Black)
                    }
                }

                // --- ADD THE NEXT QUESTION BUTTON HERE ---
                if (aiFeedback.isNotEmpty()){
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            // Extract the next question from the AIs feedback
                            if (aiFeedback.contains("Next Question:")) {
                                currentQuestion = aiFeedback.substringAfter("Next Question").trim()
                            }
                            // Reset the screen for the new question
                            userAnswer = ""
                            aiFeedback = ""
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {

                        Text("Go to Next Question")
                    }
                }

            }







        }

    }

    }


