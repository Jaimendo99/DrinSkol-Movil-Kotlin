package drinskol.app.drinksolv1


import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import drinskol.app.drinksolv1.models.Drink
import drinskol.app.drinksolv1.apiService.drinkService
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage


import okhttp3.*

//@Preview(showBackground = true, device = "id:pixel_4_xl")
@Composable
fun Drinks(navigationController: NavHostController= NavHostController(context = LocalContext.current)) {
    DrinkList(navigationController)
}


val drink_default = Drink(
    10,
    "Pina Colada",
    "The Piña Colada cocktail is a tropical blend of rum, coconut cream, and pineapple juice. It offers a refreshing and creamy taste, reminiscent of a sunny beach getaway. Sip on this delightful concoction for a momentary escape to a tropical paradise.",
    "https://firebasestorage.googleapis.com/v0/b/drinskolapi.appspot.com/o/test_168854749403518.png?alt=media",
    7.3f)
@OptIn(ExperimentalMaterial3Api::class)
//@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, device = "id:pixel_4_xl", showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE
)

@Composable
fun drinkCard(drink: Drink = drink_default, navigationController: NavHostController=NavHostController(context = LocalContext.current)) {
    val paddingValue = 10.dp
    var checked by remember {
        mutableStateOf(false)
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),

        onClick = { /* Ignoring onClick */ },
        modifier = Modifier
            .height(200.dp)
            .padding(paddingValue)
            .fillMaxWidth()
            .background(Color.Transparent),

        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .fillMaxWidth()
//                .shadow(3.dp)


        ) {
            AsyncImage(
                model = drink.img_src,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.small)
                    .shadow(2.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text(
                    drink.name,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 10.dp),
//                        .size(100.dp),
                    fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.round_local_bar_24), contentDescription = "Yes")

                    Text(
                        drink.alcohol_porcentage.toString()+"%",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .padding(horizontal = 5.dp),
                    )

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

//                        verticalAlignment = Alignment.CenterVertically



                ) {
                    IconToggleButton(checked = checked, onCheckedChange ={
                        checked = it
                    } ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .clickable {
                                    checked = !checked
                                },
                            tint = if (checked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                        )

                    }
                    ElevatedButton(onClick = { navigationController.navigate("drink/"+drink.id) }
                        ,modifier = Modifier
                            .padding(start = 20.dp),

                        ) {
                        Text(text = "Details")


                    }
                }

            }
        }


    }

}


//@Preview
@Composable
fun DrinkList(navigationController: NavHostController) {
    val service = drinkService()
    val drinksResult: MutableState<List<Drink>> = remember { mutableStateOf(emptyList()) }

    LaunchedEffect(Unit) {
        val result = service.getDrinks()
        drinksResult.value = result
    }
    Column {
        Text(text = "Drinks", fontSize = 30.sp, modifier = Modifier.padding(10.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp)
                .clip(MaterialTheme.shapes.medium)
//            .background(MaterialTheme.colorScheme.onPrimary)

        ){
            for (drink in drinksResult.value) {
                drinkCard(drink, navigationController)
//            Divider()
            }
        }
    }
}


@Preview
@Composable
fun navigation(modifier: Modifier=Modifier) {
    var selectedItem by remember {
        mutableStateOf(0)
    }
    val items = listOf("Songs", "Artists", "Playlists")

    Column(modifier = modifier){
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(imageVector = getIconForItem(index), contentDescription = item) },
                    label = { Text(text = item) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index }
                )
            }
        }
    }
}

@Composable
fun getIconForItem(index: Int): ImageVector {
    // Return the appropriate icon based on the index or customize as needed
    return when (index) {
        0 -> Icons.Default.Favorite
        1 -> Icons.Default.Person
        2 -> Icons.Default.List
        else -> Icons.Default.Favorite
    }
}
