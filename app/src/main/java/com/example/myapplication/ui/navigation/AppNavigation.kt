package com.example.myapplication.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapplication.ui.screen.dataScreen.DataScreen
import com.example.myapplication.ui.screen.formScreen.FormScreen
import com.example.myapplication.ui.screen.homeScreen.HomeScreen
import com.example.myapplication.ui.screen.informationScreen.ElectionInfoScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigationGraph(navController: NavHostController, onExit: () -> Unit) {
    NavHost(navController = navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(navController, onExit)
        }
        composable(Routes.FORM_SCREEN) {
            FormScreen(navController)
        }
        composable(Routes.DATA_SCREEN){
            DataScreen(navController)
        }
        composable(Routes.ELECTION_INFO_SCREEN){
            ElectionInfoScreen(navController)
        }
    }
}
