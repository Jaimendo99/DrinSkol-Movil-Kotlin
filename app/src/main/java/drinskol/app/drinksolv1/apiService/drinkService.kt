package drinskol.app.drinksolv1.apiService

import drinskol.app.drinksolv1.models.DetailsDrink
import drinskol.app.drinksolv1.models.Drink
import drinskol.app.drinksolv1.models.Ingredient
import drinskol.app.drinksolv1.models.Recipe
import drinskol.app.drinksolv1.models.Tags
import drinskol.app.drinksolv1.models.mapJsonToDetailsDrink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import org.json.JSONArray
import org.json.JSONObject

class drinkService {
    private val client = OkHttpClient()

    suspend fun getDrinks(): List<Drink> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://test.jaimelabs.com/api/drink")
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return@withContext parseDrinksResponse(responseBody)
            } else {
                throw IOException("Request failed: ${response.code} ${response.message}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to execute request: ${e.message}")
        }
    }

    suspend fun getDrink(id:Int):DetailsDrink = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://test.jaimelabs.com/api/drink/$id")
            .build()
        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return@withContext mapJsonToDetailsDrink(responseBody)
            } else {
                throw IOException("Request failed: ${response.code} ${response.message}")
            }
        } catch (e: IOException) {
            throw IOException("Failed to execute request: ${e.message}")
        }
    }


//    private fun parseDrinkResponse(responseBody: String?): DetailsDrink {
//        val recipes = mutableListOf<Recipe>()
//        val tags = mutableListOf<Tag>()
//
//            val jsonObject = JSONArray(responseBody).getJSONObject(0)
//            val id = jsonObject.getInt("id")
//            val name = jsonObject.getString("name")
//            val description = jsonObject.getString("description")
//            val imgSrc = jsonObject.getString("image_src")
//            val alcoholPercentage = jsonObject.getDouble("alcohol_porcentage").toFloat()
//
//            val drink = Drink(id, name, description, imgSrc, alcoholPercentage)
//
//        for (i in 0 until jsonObject.getJSONArray("recipes").length()) {
//            val recipe = jsonObject.getJSONArray("recipes").getJSONObject(i)
//
//            val id = recipe.getInt("id")
//            val description: String = recipe.getString("description")
//            val order:Int = recipe.getInt("order")
//            val quantity:Float = recipe.getDouble("quantity").toFloat()
//            val unit:String = recipe.getString("unit")
//            val ingredient_res:JSONObject  = recipe.getJSONObject("ingredient")
//            val ingredient:Ingredient = parseIngredientResponse(ingredient_res.toString())
//            val drink_id:Int = recipe.getInt("drink_id")
//
//            val recipeObject = Recipe(id, description, order, quantity, unit, drink_id , ingredient)
//            recipes.add(recipeObject)
//        }
//
//        for (i in 0 until jsonObject.getJSONArray("tags").length()) {
//            val tag = jsonObject.getJSONArray("tags").getJSONObject(i)
//
//            val id = tag.getInt("id");
//            val name = tag.getString("name");
//
//            val tagObject = Tag(id, name);
//            tags.add(tagObject)
//        }
//
//        val drinkdetail:DetailsDrink = DetailsDrink(drink, recipes, tags)
//        return drinkdetail
//        }


    private fun parseDrinksResponse(responseBody: String?): List<Drink> {
            val drinks = mutableListOf<Drink>()

            responseBody?.let {
                val jsonArray = JSONArray(it)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id = jsonObject.getInt("id")
                    val name = jsonObject.getString("name")
                    val description = jsonObject.getString("description")
                    val imgSrc = jsonObject.getString("image_src")
                    val alcoholPercentage = jsonObject.getDouble("alcohol_porcentage").toFloat()

                    val drink = Drink(id, name, description, imgSrc, alcoholPercentage)
                    drinks.add(drink)
                }
            }

            return drinks
        }


}