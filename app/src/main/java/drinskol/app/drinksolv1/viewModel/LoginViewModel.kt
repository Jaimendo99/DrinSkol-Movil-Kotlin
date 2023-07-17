package drinskol.app.drinksolv1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import drinskol.app.drinksolv1.apiService.UserService
import drinskol.app.drinksolv1.models.LogToken
import drinskol.app.drinksolv1.models.mapJsonToToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String>
        get() = _username

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String>
        get() = _password

    private val _token = MutableLiveData<LogToken>()
    val token: MutableLiveData<LogToken>
        get() = _token


    fun setUsername(username: String, password: String) {
        _username.value = username
        _password.value = password
    }



    fun login(navigationController: NavHostController) {
        viewModelScope.launch {
            try {
                val service = UserService()
                val token: LogToken = withContext(Dispatchers.IO) {
                    service.login(_username.value!!, _password.value!!)
                }
                _token.value = token
                withContext(Dispatchers.Main) {
                    navigationController.navigate("drinks")
                }
            } catch (e: Exception) {
                // Handle exceptions and display error message
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

}