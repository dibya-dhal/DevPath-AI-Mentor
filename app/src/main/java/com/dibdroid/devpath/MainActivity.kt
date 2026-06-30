package com.dibdroid.devpath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.dibdroid.devpath.ui.navigation.DevpathNavGraph
import com.dibdroid.devpath.ui.navigation.Screen
import com.dibdroid.devpath.ui.navigation.bottomNavItems
import com.dibdroid.devpath.ui.screens.OnboardingScreen
import com.dibdroid.devpath.ui.theme.DevPathTheme
import okhttp3.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevPathTheme(darkTheme = false) {
                val navController = androidx.navigation.compose.rememberNavController()

                //This "Listens" to the navigation changes
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != Screen.Onboarding.route) {
                            BottomNavigationBar(navController,currentRoute)
                        }
                    }

                ) { innerPadding ->
                    androidx.compose.foundation.layout.Box(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()) {
                       // OnboardingScreen(onNavigateToHome = {})
                        DevpathNavGraph(navController = navController)
                    }
                }

                }
            }
        }
    }

/**
 * Professional Bottom Navigation Component
 * Defined outside the class so it can be reused
 *
 */

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {

        // Loop through the items we defined in Navigation.kt
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                label = {Text(text = screen.title ?: "") },
                icon = {
                    Icon(
                        imageVector = screen.icon ?: Icons.Default.Home,
                        contentDescription = screen.title
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route){
                        // Standard Professional Navigation logic:
                        // 1. Pop up to the main start destination to avoid stack build up
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true


                        }
                        //2. Avoid multiple copies of the same screen
                        launchSingleTop = true
                        // 3. restore state when re-selecting a previous screen
                        restoreState = true

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF214EF3),
                    selectedTextColor = Color(0xFF214EF3),
                    indicatorColor = Color(0xFf214EF3).copy(alpha = 0.1f),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }

    }

}








@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPreview(){
    DevPathTheme(darkTheme = false) {
        val navController = androidx.navigation.compose.rememberNavController()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            androidx.compose.foundation.layout.Box(modifier = Modifier
                .padding(innerPadding)) {
               // OnboardingScreen(onNavigateToHome = {})
                DevpathNavGraph(navController = navController)
            }
        }

    }
}