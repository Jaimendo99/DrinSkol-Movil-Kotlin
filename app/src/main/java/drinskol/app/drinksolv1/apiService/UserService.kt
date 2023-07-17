package drinskol.app.drinksolv1.apiService

import androidx.navigation.NavHostController
import drinskol.app.drinksolv1.models.LogToken
import drinskol.app.drinksolv1.models.User
import drinskol.app.drinksolv1.models.mapJsonToToken
import drinskol.app.drinksolv1.models.mapJsonToUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

class UserService {
    private val client = OkHttpClient()

    suspend fun login(username: String, password: String): LogToken {
        val url = "https://test.jaimelabs.com/api/user/login"

        val mediaType = "application/json".toMediaTypeOrNull()
        val body =
            "{\n  \"username\": \"$username\",\n  \"password\": \"$password\"\n}".toRequestBody(mediaType)

        // Build the HTTP request
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        println("UserService: $password, $username")

        return withContext(Dispatchers.IO) {
            try {
                // Execute the request
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                println(responseBody)

                if (response.isSuccessful) {
                    mapJsonToToken(responseBody)
                } else {
                    val errorMessage = "Request failed: ${response.code} ${response.message}"
                    println(errorMessage)
                    throw IOException(errorMessage)
                }
            } catch (e: IOException) {
                val errorMessage = "Failed to execute request: ${e.message}"
                println(errorMessage)
                throw IOException(errorMessage)
            }
        }
    }


    suspend fun register(username: String, password: String, name: String, lastname: String, email: String, birth_date: String, image_src: String): User {
        val url = "https://test.jaimelabs.com/api/user/register"

        val mediaType = "application/json".toMediaTypeOrNull()
        val body =
            "{\n  \"username\": \"$username\",\n  \"password\": \"$password\",\n  \"name\": \"$name\",\n  \"last_name\": \"$lastname\",\n  \"email\": \"$email\",\n  \"birth_date\": \"$birth_date\",\n  \"image_src\": \"$image_src\",\n \"id_admin\": \"false\"}".toRequestBody(mediaType)

        // Build the HTTP request
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        return withContext(Dispatchers.IO) {
            try {
                // Execute the request
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful) {
                    mapJsonToUser(responseBody)
                } else {
                    val errorMessage = "Request failed: ${response.code} ${response.message}"
                    println(errorMessage)
                    throw IOException(errorMessage)
                }
            } catch (e: IOException) {
                val errorMessage = "Failed to execute request: ${e.message}"
                println(errorMessage)
                throw IOException(errorMessage)
            }
        }
    }
}
