package com.dibdroid.devpath.ui.screens

import com.dibdroid.devpath.R
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DevPath",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF214EF3)
                    )
                }, actions = {
                    IconButton(onClick = { /*Notification action here*/}) {
                        Icon(imageVector = Icons.Default.Notifications,
                            contentDescription = "Notification",
                            tint = Color(0xFF214EF3))
                    }

                }
            )
        }
    ) { inneerPadding ->
        Column(
            modifier = Modifier.padding(inneerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Make the screen scrollable
                .padding(horizontal = 20.dp) //
        ) {
            Spacer(modifier = Modifier.height(15.dp))// New Spacer for HI NAME

            // 1. Greeting
            Text(text = "Hi Dev 👋", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = Color.Black)
            Text(text = "Let's keep learning!", fontSize = 16.sp,
                color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Current Path Card
            PathStatusCard()

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Progress Sec
            PrgressSection(progrss = 0.6f)  // 60% progress

            Spacer(modifier = Modifier.height(24.dp))

            // 4. today task
            TakeSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Daily Tip
            DailyTipCard()

            Spacer(modifier = Modifier.height(32.dp))


        }
    }


}

@Composable
fun PathStatusCard() {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = (0.1f))),
                   contentAlignment = Alignment.Center
            ) {
                Icon(painter = painterResource(R.drawable.img_android),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Unspecified

                )

            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {

                Text(text = "Current Path", fontSize = 12.sp,
                    color = Color.Black)
                Text(text = "Android Developer", fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black)


            }
            Icon(imageVector = Icons.Default.ChevronRight,
                contentDescription = null, tint = Color.Gray)
        }
    }
}

@Composable
fun PrgressSection(progrss : Float) {
    Column {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween) {
          Text(text = "Your Progress", fontSize = 18.sp,
              fontWeight = FontWeight.SemiBold, color = Color.Black)
            Text(text = "60%", color = Color.Black,
                fontWeight = FontWeight.Bold)

        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = {progrss},
            modifier = Modifier.fillMaxWidth().height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary
                .copy(alpha = 0.1f)
        )
    }

}





@Composable
fun TakeSection() {
    Column {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Today's Tasks", fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
            TextButton(onClick = {}) {Text("See all") }
        }
        TaskItem("Learn Kotlin Basics", color = Color.Black)
        TaskItem("Build UI Layout", color = Color.Black)
        TaskItem("Practice DSA", color = Color.Black)



    }
}

@Composable
fun TaskItem(title : String, color: Color){
    var isChecked by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(vertical = 4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isChecked, onCheckedChange = {isChecked = it})
          Text(text = title, fontSize = 14.sp, color = Color.Black,
              fontStyle = FontStyle.Normal, fontWeight = FontWeight.Bold )

        }
    }

}

@Composable
fun DailyTipCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor =
            MaterialTheme.colorScheme.primary.copy(0.05f))
    ) {
        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = Color(0xFF3F51B5)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Daily tip", fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
                Text(text = "Consistency beats intensity.", fontSize = 14.sp,
                    color = Color.Black)
                Text(text = "Learn a little every day!", fontSize = 14.sp,
                    color = Color.Black)
            }
        }
    }
}
