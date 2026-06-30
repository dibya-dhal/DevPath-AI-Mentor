package com.dibdroid.devpath.ui.screens

import android.R
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgrssScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My progress", fontWeight = FontWeight.Bold, color = Color(0xFF214EF3)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 1. STATS GRID (2*2)
            Row(modifier = Modifier.fillMaxWidth()) {
                StatCard("Days Active", "25", Icons.Default.CalendarMonth, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                StatCard("Completion", "60%", Icons.Default.DonutLarge, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    StatCard("Job Applied", "10", Icons.Default.Work, Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(16.dp))
                    StatCard("Interviews","3", Icons.Default.People, Modifier.weight(1f))
                }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. DETAILED PROGRESS
            Text("Detailed Progress", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            ProgressRow("Topic progress", 0.7f, "70%")
            Spacer(modifier = Modifier.height(16.dp))
            ProgressRow("DSA Progress", 0.55f, "55%")

            Spacer(modifier = Modifier.height(32.dp))

            // 3. STREAK CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF98000).copy(alpha = 0.1f))
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = Color(0xFFFF9800))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("5 Day Streak!", fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // 4. WEEKLY SUMMARY

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Weekly Summary", fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Great job! You completed 5 tasks this week and improved your DSA score by 10%.",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

            }
               Spacer(modifier = Modifier.height(50.dp))



        }

    }

}

@Composable
fun StatCard(lable : String, value : String, icon : ImageVector, modifier: Modifier){
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = Color(0xFF214EF3), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = lable, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)


        }

    }
}

@Composable
fun ProgressRow(lable: String, progress : Float, percent : String) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(lable, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(percent, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = {progress},
            modifier = Modifier.fillMaxWidth().height(8.dp),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )

    }
}