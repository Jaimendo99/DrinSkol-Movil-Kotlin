package drinskol.app.drinksolv1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import drinskol.app.drinksolv1.apiService.ImageService
import drinskol.app.drinksolv1.apiService.UserService
import drinskol.app.drinksolv1.models.User
import drinskol.app.drinksolv1.models.fb_image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel: ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String>
        get() = _username

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String>
        get() = _password

    private val _name = MutableLiveData<String>()
    val name: MutableLiveData<String>
        get() = _name

    private val _lastname = MutableLiveData<String>()
    val lastname: MutableLiveData<String>
        get() = _lastname

    private val _email = MutableLiveData<String>()
    val email: MutableLiveData<String>
        get() = _email

    private val _birth_date = MutableLiveData<String>()
    val birth_date: MutableLiveData<String>
        get() = _birth_date

    private val _image_src = MutableLiveData<String>()
    val image_src: MutableLiveData<String>
        get() = _image_src

    private val _img_64 = MutableLiveData<String>()
    val img_64: MutableLiveData<String>
        get() = _img_64




   fun setForms(username: String, password: String, name: String, lastname: String, email: String ) {
        _username.value = username
        _password.value = password
        _name.value = name
        _lastname.value = lastname
        _email.value = email
    }

    fun setBirthDate(birth_date: String) {
        _birth_date.value = birth_date
    }
    fun setImageSrc(image_src: String) {
        _image_src.value = image_src
    }

    fun setImage64(img_64: String) {
        _img_64.value = img_64
    }
    fun uploadImage(img_64: String){
        viewModelScope.launch {
            try {
                val imageService = ImageService()
                val image: fb_image = withContext(Dispatchers.IO) {
                    imageService.uploadImage(img_64)
                }
                setImageSrc(image.image_src)
            }catch (
                e: Exception
            ) {
                println("Error: " + e.message)
                println("Error: " + e.localizedMessage)
                    println("StackTrace:")
                var stack:String = ""
                for (element in e.stackTrace) {
                    stack += element.toString()+"\n"

                }
                println(stack)
            }
        }
    }

    fun register(navHostController: NavHostController) {
        uploadImage(_img_64.value!!)
        viewModelScope.launch {
            try {
                val userService = UserService()
                val user: User = withContext(Dispatchers.IO) {
                    userService.register(_username.value!!, _password.value!!, _name.value!!, _lastname.value!!, _email.value!!, _birth_date.value!!, _image_src.value!!)
                }
                navHostController.navigate("login")
            }catch (
                e: Exception
            ) {
                println("Error: " + e.message)
                println("Error: " + e.localizedMessage)
                    println("StackTrace:")
                var stack:String = ""
                for (element in e.stackTrace) {
                    stack += element.toString()+"\n"

                }
                println(stack)
            }
        }
    }


    fun test() {

        println(
            "Username: " + _username.value + "\n" +
                    "Password: " + _password.value + "\n" +
                    "Name: " + _name.value + "\n" +
                    "Lastname: " + _lastname.value + "\n" +
                    "Email: " + _email.value + "\n" +
                    "Birth Date: " + _birth_date.value + "\n" +
                    "Image Src: " + _image_src.value + "\n" +
                    "Image 64: " + _img_64.value + "\n"

        )
    }
}