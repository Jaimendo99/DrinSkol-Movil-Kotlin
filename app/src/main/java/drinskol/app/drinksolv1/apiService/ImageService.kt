package drinskol.app.drinksolv1.apiService

import drinskol.app.drinksolv1.models.fb_image
import drinskol.app.drinksolv1.models.mapJsonToImgResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ImageService {
    private val client = OkHttpClient()

    suspend fun uploadImage(image_64: String):fb_image {
        val url = "https://test.jaimelabs.com/api/image"
        val mediaType = "application/json".toMediaTypeOrNull()
        val timestamp: String = System.currentTimeMillis().toString()
        val body_str = """
                
                    "image": "$image_64",
                    "class_name": "user",
                    "class_id": "$timestamp",
                    "image_extension": "png"
                }
                """.trimIndent()
        println(body_str)

        val body =
            "{\n\t\"image\":\"${image_64}\",\n\t\"class_name\":\"user\",\n\t\"class_id\":\"${timestamp}\",\n\t\"image_extension\":\"png\"\n}".toRequestBody(
                mediaType
            )

//        val body = body_str.toRequestBody(mediaType)
        val request = okhttp3.Request.Builder()
            .url(url)
            .post(body)
            .build()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                println(responseBody)
                if (response.isSuccessful) {
                    mapJsonToImgResponse(responseBody)
                } else {
                    val errorMessage = "Request failed: ${response.code} ${response.message} ${response.body }"
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