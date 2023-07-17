package drinskol.app.drinksolv1.apiService

import drinskol.app.drinksolv1.models.Ingredient
import drinskol.app.drinksolv1.models.mapJsonToIngredient
import drinskol.app.drinksolv1.models.mapJsonToIngredients
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONArray

class IngredientService {
    private val client = OkHttpClient()

    suspend fun getIngredients(): List<Ingredient> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://test.jaimelabs.com/api/ingredient")
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return@withContext mapJsonToIngredients(responseBody)
            } else {
                throw IOException("Request failed: ${response.code} ${response.message}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to execute request: ${e.message}")
        }
    }


}
//    fun parseIngredientsResponse(responseBody: String?): List<Ingredient> {
//            val ingredients = mutableListOf<Ingredient>()
//
//            responseBody?.let {
//                val jsonArray = JSONArray(it)
//                for (i in 0 until jsonArray.length()) {
//                    val jsonObject = jsonArray.getJSONObject(i)
//                    val id = jsonObject.getInt("id")
//                    val name = jsonObject.getString("name")
//                    val description = jsonObject.getString("description")
//                    val imgSrc = jsonObject.getString("image_src")
//                    val alcoholPercentage = jsonObject.getDouble("alcohol_porcentage").toFloat()
//
//                    val ingredient = Ingredient(id, name, description, imgSrc, alcoholPercentage)
//                    ingredients.add(ingredient)
//                }
//            }
//
//            return ingredients
//        }

