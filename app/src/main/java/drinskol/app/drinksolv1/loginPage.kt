package drinskol.app.drinksolv1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import drinskol.app.drinksolv1.viewModel.LoginViewModel


@Preview(showBackground = true, device = "id:pixel_4_xl")
@Composable
fun loginPage(navigationController: NavHostController = NavHostController(context = LocalContext.current), loginViewModel: LoginViewModel= LoginViewModel()) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 40.dp, vertical = 150.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center

    ) {
       Card(
           modifier = Modifier
           .padding(horizontal = 10.dp, vertical = 15.dp),
           elevation = CardDefaults.cardElevation(
               defaultElevation = 10.dp
           ),
           shape = MaterialTheme.shapes.medium
       ){
           Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
           ) {

           Header(modifier = Modifier.padding(10.dp))
           Spacer(modifier =Modifier.height(20.dp))
           login_forms(modifier = Modifier.fillMaxWidth(), loginViewModel)
           Spacer(modifier = Modifier.height(20.dp))
           loginButton(Modifier.fillMaxWidth(), navigationController, loginViewModel)

           }
       }
    }
}

@Composable
fun Header(modifier: Modifier ){
    Column {
        SvgImageSample(modifier, 250)
//        Text(text = "Ingresa tus credenciales", modifier = modifier)
    }
}

@Composable
fun SvgImageSample(modifier:Modifier, size_width:Int = 100) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data("https://firebasestorage.googleapis.com/v0/b/drinskolapi.appspot.com/o/drinskolLogo.svg?alt=media")
            .build()
    )
    Image(modifier = Modifier
            .size(size_width.dp),
        painter = painter,
        contentDescription = null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun login_forms(modifier: Modifier, loginViewModel: LoginViewModel) {

    val username:String by loginViewModel.username.observeAsState(initial ="")
    val password:String by loginViewModel.password.observeAsState(initial ="")


    Column(modifier = modifier
        .fillMaxWidth(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp),
            colors= OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
            ),

            value = username,
            onValueChange = {loginViewModel.setUsername(it, password)},
            label = { Text("Username") }
        )
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
            colors= OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.inverseOnSurface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
            ),

            value = password,
            onValueChange = {loginViewModel.setUsername(username, it)},
            label = { Text("Password") }
        )
    }
}

@Composable
fun loginButton(modifier: Modifier, navigationController: NavHostController? = null, loginViewModel: LoginViewModel) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = {
                if (navigationController != null) {
                    loginViewModel.login(navigationController)
                }
            }
        ) {
            Text("Login")

        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = { navigationController?.navigate("register") }) {
        Text("Sing Up")
        }

}
}


