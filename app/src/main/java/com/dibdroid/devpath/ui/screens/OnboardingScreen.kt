package com.dibdroid.devpath.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.dibdroid.devpath.R


data class DevPath(val name : String, val icon : Int)

@Composable
fun OnboardingScreen(onNavigateToHome : () -> Unit) {

    val paths = listOf(
        DevPath("Android Dev", R.drawable.img_android),
        DevPath("Web Dev", R.drawable.img_web),
        DevPath("Backend Dev", R.drawable.img_backend),
        DevPath("Data Science", R.drawable.img_datascience)

    )

    var selectPath by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))

6
            Icon(painter = painterResource(R.drawable.img),
            contentDescription = "Logo",
            modifier = Modifier.size(130.dp),
            tint = MaterialTheme.colorScheme.primary)




        Text(
            text = "DevPath",
            fontSize = 33.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Your AI Guide to First Job",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choose Your Path",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontStyle = FontStyle.Normal
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Grid layout

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
                               .fillMaxWidth()
                .heightIn(max = 400.dp)

        ) {
            items(paths){ path ->
                PathCard(
                    path = path,
                    isSelected = selectPath == path.name,
                    onClick = {selectPath = path.name}
                )

            }

        }

        Button(
            onClick = {onNavigateToHome()},
            modifier = Modifier.fillMaxWidth()
                .height(56.dp),
            enabled = selectPath.isNotEmpty(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(text = "Get Started", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))

    }

}

/**
 * Component : PathCard
 *
 *
 */

@Composable
fun PathCard(path: DevPath, isSelected : Boolean, onClick : () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.height(130.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.
            primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme
                .surface,
        ),

        border = if (isSelected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = path.icon),
                contentDescription = path.name,
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = path.name,
                fontSize = 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.onSurface
            )
        }

    }

}