package drinskol.app.drinksolv1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import drinskol.app.drinksolv1.apiService.drinkService
import drinskol.app.drinksolv1.models.DetailsDrink
import drinskol.app.drinksolv1.models.Drink
import drinskol.app.drinksolv1.models.Ingredient
import drinskol.app.drinksolv1.models.Recipe
import drinskol.app.drinksolv1.models.Tags

//@Preview(showBackground = true, device = "id:pixel_4_xl")

val emptyDetailsDrink = DetailsDrink(
    id = 0,
    name = "",
    description = "",
    img_src = "",
    alcohol_percentage = 0.0f,
    recipes = emptyList(),
    tags = emptyList()
)

@Composable
fun DrinkPage(drinkId: String = "15", navigationController: NavHostController) {
    val drink_service: drinkService = drinkService()
    val drink: MutableState<DetailsDrink> = remember { mutableStateOf(emptyDetailsDrink)}

    LaunchedEffect(Unit){
        val result = drink_service.getDrink(drinkId.toInt())
        drink.value = result
    }


    }