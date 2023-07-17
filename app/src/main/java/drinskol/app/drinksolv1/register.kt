package drinskol.app.drinksolv1


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.content.ContentProviderCompat.requireContext
import drinskol.app.drinksolv1.viewModel.RegisterViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.io.ByteArrayOutputStream



@Preview(showBackground = true, device = "id:pixel_4_xl")
@Composable
fun Register(navigationController: NavHostController = NavHostController(LocalContext.current), registerViewModel: RegisterViewModel= RegisterViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(enabled = true, state = rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        PhotoField(registerViewModel)
        Spacer(modifier = Modifier.height(15.dp))
        userInfoForm(registerViewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {registerViewModel.register(navigationController)}) {
            Text(text = "Register")
        }
        TextButton(onClick = {navigationController.navigate("login")}){
            Text(text = "Already have an account? Login")
        }
        }
}


@Composable
fun PhotoField(registerViewModel: RegisterViewModel, defaultImage: String? ="https://firebasestorage.googleapis.com/v0/b/drinskolapi.appspot.com/o/Default_profile_pic.png?alt=media") {

    val image_src: String? by registerViewModel.image_src.observeAsState(defaultImage)
    registerViewModel.setImageSrc(defaultImage!!)

    val openDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .background(Color.LightGray.copy(alpha = .5f), CircleShape)
            .padding(4.dp)
    ) {
        AsyncImage(
            model = image_src!!,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .fillMaxSize()
                .clip(CircleShape)
//                .shadow(2.dp)
        )
        photoOptions(openDialog, registerViewModel)
        IconButton(
            onClick = { openDialog.value = true },
            modifier = Modifier
                .shadow(2.dp, CircleShape)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .padding(horizontal = 6.dp, vertical = 6.dp)
                .size(35.dp)
                .align(Alignment.BottomEnd),
        ){
            Icon(
                modifier=Modifier
                    .size(35.dp),

                imageVector = Icons.Rounded.Add,
                contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@SuppressLint("UnrememberedMutableState", "SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userInfoForm(registerViewModel: RegisterViewModel = RegisterViewModel()) {

    val username:String by registerViewModel.username.observeAsState("")
    val password:String by registerViewModel.password.observeAsState("")
    val name:String by registerViewModel.name.observeAsState("")
    val lastName:String by registerViewModel.lastname.observeAsState("")
    val email:String by registerViewModel.email.observeAsState("")
    val birth_date:String by registerViewModel.birth_date.observeAsState("")

    val datePickerState = rememberDatePickerState(0)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        OutlinedTextField(modifier=Modifier
            .fillMaxWidth(),
            value = username,
            onValueChange = { registerViewModel.setForms(
                username = it,
                password = password,
                name = name,
                lastname = lastName,
                email = email
            ) },
            label = { Text(text = "Username") }
        )

        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth(),
            value = password,
            onValueChange = { registerViewModel.setForms(
                username = username,
                password = it,
                name = name,
                lastname = lastName,
                email = email
            ) },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, contentDescription = null)
                }
            }
        )
        OutlinedTextField(modifier=Modifier
            .fillMaxWidth(),
            value = name,
            onValueChange = { registerViewModel.setForms(
                username = username,
                password = password,
                name = it,
                lastname = lastName,
                email = email
            )},
            label = { Text(text = "Name") }
        )

        OutlinedTextField(modifier=Modifier
            .fillMaxWidth()
            ,value = lastName,
            onValueChange = { registerViewModel.setForms(
                username = username,
                password = password,
                name = name,
                lastname = it,
                email = email
            )},
            label = { Text(text = "Last Name") }
        )

        OutlinedTextField(modifier=Modifier
            .fillMaxWidth()
            ,value = email,
            onValueChange = { registerViewModel.setForms(
                username = username,
                password = password,
                name = name,
                lastname = lastName,
                email = it
            ) }
            ,label = { Text(text = "Email") }
        )
        val openDialog = remember { mutableStateOf(false) }

        datePickerPopUp(datePickerState, openDialog)

        val date:String = if (datePickerState.selectedDateMillis == 0.toLong()) {
            "Select Date"
        } else {
            val date = datePickerState.selectedDateMillis?.let { Date(it) }
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            registerViewModel.setBirthDate(formatter.format(date))
            formatter.format(date)
    }

        OutlinedTextField(
            value = date,
            onValueChange = {registerViewModel.setBirthDate(date)},
            leadingIcon = {
                IconButton(onClick = { openDialog.value = true }) {
                    
                Icon(
                    painter = painterResource(id = R.drawable.baseline_calendar_month_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
//            placeholder = { Text(text = birthdate) },
            label = { Text(text = "Birthdate") },
            readOnly = true,
        )
    }

}




@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun datePickerPopUp(datePickerState: DatePickerState, openDialog: MutableState<Boolean>) {
    if (openDialog.value) {
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {openDialog.value = false},
            confirmButton = {TextButton(onClick = {
                        openDialog.value = false
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = {openDialog.value = false}){
                    Text("Cancel") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}


@Composable
fun photoTaker(modifier: Modifier, registerViewModel: RegisterViewModel) {

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->

        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val img_base64 = Base64.encodeToString(b, Base64.DEFAULT)

        registerViewModel.setImage64(img_base64)
        println(img_base64.subSequence(0, 100))
        registerViewModel.img_64.value?.let { println(it.subSequence(0, 100)) }

    }
    IconButton(modifier = modifier,
        onClick = { launcher.launch() }) {
        Icon(modifier = Modifier
            .fillMaxWidth()
            .size(40.dp)
                ,
            imageVector = Icons.Rounded.CameraAlt,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

    }

    }


@Composable
fun galleryPhoto(modifier:Modifier){
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->


        // Handle the returned Uri
    }

    IconButton(modifier = modifier,
        onClick = { launcher.launch("image/*") }) {
        Icon(modifier = Modifier
            .fillMaxWidth()
            .size(40.dp)
                ,
            imageVector = Icons.Rounded.PhotoLibrary,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }

    }



@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun photoOptions(openDialog: MutableState<Boolean> = remember { mutableStateOf(true) }, registerViewModel: RegisterViewModel= RegisterViewModel()){
//    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            Surface(
                modifier = Modifier,
//                    .wrapContentWidth()
//                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Select an option")
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                        Column (
                            modifier = Modifier

                                .padding(start = 20.dp, end = 15.dp, top = 10.dp),
                            verticalArrangement = Arrangement.Center

                                ){
                            photoTaker(Modifier, registerViewModel)
                            Text(text = "Camera")
                        }
                        Column (
                            modifier = Modifier
                                .padding(start = 20.dp, end = 15.dp, top = 10.dp),
                            verticalArrangement = Arrangement.Center
                                ){
                            galleryPhoto(Modifier)
                            Text(text = "Gallery")
                        }

                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }

}