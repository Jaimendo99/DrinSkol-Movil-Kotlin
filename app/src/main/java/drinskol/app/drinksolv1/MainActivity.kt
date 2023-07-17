package drinskol.app.drinksolv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import drinskol.app.drinksolv1.ui.theme.Drinksolv1Theme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import drinskol.app.drinksolv1.viewModel.LoginViewModel
import drinskol.app.drinksolv1.viewModel.RegisterViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Drinksolv1Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.onPrimary){
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = "login"){

                        composable("login"){ loginPage(navigationController, LoginViewModel())}

                        composable("drinks"){ Drinks(navigationController)}

                        composable("drink/{drinkId}"){ backStackEntry ->
                            val drinkId = backStackEntry.arguments?.getString("drinkId")
                            DrinkPage(drinkId = drinkId!!, navigationController)
                        }

                        composable("ingredients"){ Ingredients(navigationController) }

                        composable("ingredient/{ingredientId}"){ backStackEntry ->
                            val ingredientId = backStackEntry.arguments?.getString("ingredientId")
                            Ingredient(ingredientId = ingredientId!!, navigationController)
                        }

                        composable("profile/{userId}"){ backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
                            Profile(userId = userId!!, navigationController)
                        }

                        composable("register"){ Register(navigationController, RegisterViewModel())}
                    }

                }
            }
        }
    }
}




@Preview
@Composable
fun Colors_material(){
    Column {
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.primary),
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onPrimary)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.secondary)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onSecondary)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onSecondaryContainer)
            )
        }
        Row {
            Box(modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.tertiary)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onTertiary)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onTertiaryContainer)
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onSurface)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }
    }
}