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

    NavHost(navController = navController, startDestination = Screens.MAIN.name) {
        composable(Screens.MAIN.name) { MainScreen(navController) }
        composable(Screens.NEW_GAME.name) { NewGameScreen(navController) }
        composable(Screens.LOAD_GAME.name) { LoadGameScreen(navController) }
        composable(Screens.CHOICE_TYPE_GAME.name) { ChoiceTypeGameScreen(navController) }
        composable(Screens.SINGLE.name) { AnswersListScreen(navController) }
        composable(Screens.RANDOM_CARD.name) { RandomCardScreen(navController) }
        composable(Screens.CATEGORY.name) {
            CategoryScreen(navController) { category ->
                navController.navigate("${Screens.CATEGORY.name}/${category.name}")
            }
        }
        composable(
            route = "${Screens.CATEGORY.name}/{category}",
            arguments = listOf(
                navArgument("category") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val category =
                Category.valueOf(
                    backStackEntry.arguments?.getString("category")!!
                )
            CategoryCardsScreen(navController, category)
        }
        composable("${Screens.SINGLE.name}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: return@composable
            SinglePlayerCard(navController, id)
        }
        composable(Screens.END_GAME.name) { EndGameScreen(navController) }
        composable(Screens.SINGLE.name) { AnswersListScreen(navController) }
    }
}