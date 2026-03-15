package com.example.gamecandor.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamecandor.model.Category

@Composable
fun GameApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("new_game") { NewGameScreen(navController) }
        composable("load_game") { LoadGameScreen(navController) }
        composable("choice_type_game") { ChoiceTypeGameScreen(navController) }
        composable("solo_game") { SoloScreen(navController) }
        composable("random_card") { RandomCardScreen(navController) }
        composable("category_screen") {
            CategoryScreen { category ->
                navController.navigate("cards_screen/${category.name}")
            }
        }
        composable(
            route = "cards_screen/{category}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val category =
                Category.valueOf(
                    backStackEntry.arguments?.getString("category")!!
                )

            CategoryCardsScreen(category)
        }
    }
}