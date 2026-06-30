package com.dibdroid.devpath.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo


/**
 *  1. DATA MODELS
 * In a real company, this data would eventually
 * come from room Data base
 */

enum class StepsStatus{ COMPLETED, CURRENT, UPCOMING }

data class RoadmapStep(
    val id : Int,
    val title : String,
    val desc : String,
    val status : StepsStatus
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoadmapScreen() {
// 2. MOCK DATA (Matches your design image)
    val steps = listOf(
        RoadmapStep(1, "Basics", "Learn the fundamentals", StepsStatus.COMPLETED),
        RoadmapStep(2,"UI Design", "Layouts Views & Compose", StepsStatus.COMPLETED),
        RoadmapStep(3,"State Management","Understand State handling", StepsStatus.CURRENT),
        RoadmapStep(4,"API Integration","Connect your app with ApIs", StepsStatus.UPCOMING),
        RoadmapStep(5,"Database","Store and manage data", StepsStatus.UPCOMING),
        RoadmapStep(6,"Projects","Build real world projects", StepsStatus.UPCOMING)


    )
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(text = "Roadmap",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF214EF3)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.4f))
            }
        }
    ) { innerPadding ->
        //3. THE LIST
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(24.dp)) }

            itemsIndexed(steps) { index, step ->
               RoadmapItem(step = step, isLast = index == steps.size - 1)

            }

        }

    }


}

/**
 * 4. ROADMAP ITEM COMPONENT
 * This builds one ROW consisting of the icon/line on the
 * left and the card on the right
 */

@Composable
fun RoadmapItem(step: RoadmapStep, isLast : Boolean) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top
    ) {
        // LEFT SECTION: Icon and Vertical Line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(40.dp)
        ) {

            StepCircleIcon(status = step.status)

            if (!isLast){
                // This draws the line connecting to the next step
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(60.dp)
                        .background(
                            if (step.status == StepsStatus.COMPLETED)
                                Color(0xFF43A947)
                            else Color.LightGray.copy(alpha = 0.5f)

                        )
                )
            }



        }

        Spacer(modifier = Modifier.width(16.dp))

        // RIGHT SECTION : The Step Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(
                  containerColor = if (step.status == StepsStatus.COMPLETED) Color(0xFF214EF3).copy(0.05f)
                                else Color.White
              ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${step.id}. ${step.title}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (step.status == StepsStatus.CURRENT) Color(0xFF214EF3)
                                 else Color.Black
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        }
    }


}

/**
 * 5. STEP CIRCLE ICON
 * Handle Green (check), Blue(Current),
 * and Gray (upcoming) states.
 */

@Composable
fun StepCircleIcon(status: StepsStatus) {
    val color = when (status) {
        StepsStatus.COMPLETED -> Color(0xFF43A947) // Green
        StepsStatus.CURRENT -> Color(0xFF214EF3) // Blue
        StepsStatus.UPCOMING -> Color.LightGray.copy(alpha = 0.6f) //Gray

    }

    Box(modifier = Modifier
        .size(32.dp)
        .clip(CircleShape)
        .background(if (status == StepsStatus.CURRENT) Color.White else color)
        .then(

            if (status == StepsStatus.CURRENT) Modifier.background(Color.White) else Modifier
        ),
        contentAlignment = Alignment.Center
    ) {
        when (status) {
            StepsStatus.COMPLETED -> {
                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(20.dp))

            }

            StepsStatus.CURRENT -> {
                Icon(Icons.Default.RadioButtonChecked, null, tint = color,
                    modifier = Modifier.size(24.dp) )
            }

            StepsStatus.UPCOMING -> {
                 Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color.White))
            }
        }
    }
}