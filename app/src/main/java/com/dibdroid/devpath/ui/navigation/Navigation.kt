package com.dibdroid.devpath.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dibdroid.devpath.ui.screens.AiMentorScreen
import com.dibdroid.devpath.ui.screens.HomeScreen
import com.dibdroid.devpath.ui.screens.InterviewScreen
import com.dibdroid.devpath.ui.screens.OnboardingScreen
import com.dibdroid.devpath.ui.screens.ProgrssScreen
import com.dibdroid.devpath.ui.screens.RoadmapScreen


// This class stores everything about a screen in one place
sealed class Screen(
    val route: String,
    val title : String? = null,
    val icon : ImageVector? = null
) {

    // onboarding no icon or title because it doesn't show in the bottom bar
    object Onboarding : Screen("onboarding")

    // These screens WILL show int the bottom bar
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Roadmap : Screen("roadmap", "Roadmap", Icons.Default.Map)
    object Mentor : Screen("mentor","AI Mentor", Icons.Default.Chat)
    object Interview : Screen("interview","Interview", Icons.Default.Assignment)
    object Progress : Screen("progress","Progress", Icons.Default.BarChart)


}

// A List of item we want to show in the bottom navigation
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Roadmap,
    Screen.Mentor,
    Screen.Interview,
    Screen.Progress
)


@Composable
fun DevpathNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        // 1. Onboarding Screen
        composable(Screen.Onboarding.route){
            OnboardingScreen(
                onNavigateToHome = {
                    // Navigate to Home and clear the backstack
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.Onboarding.route){ inclusive = true}
                    }
                }
            )
        }

        //2. Main Screens (These will eventually show the Bottom Bar
        composable(Screen.Home.route){ HomeScreen() }
        composable(Screen.Roadmap.route){ RoadmapScreen() }
        composable(Screen.Mentor.route){ AiMentorScreen() }
        composable(Screen.Interview.route){ InterviewScreen() }
        composable(Screen.Progress.route){ ProgrssScreen() }


    }
}