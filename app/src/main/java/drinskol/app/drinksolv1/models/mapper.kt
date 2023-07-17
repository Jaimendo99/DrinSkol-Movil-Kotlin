package drinskol.app.drinksolv1.models

import com.google.gson.Gson

fun mapJsonToDrink(json: String?): Drink {
    val gson = Gson()
    return gson.fromJson(json, Drink::class.java)
}

fun mapJsonToDetailsDrink(json: String?): DetailsDrink {
    val gson = Gson()
    return gson.fromJson(json, DetailsDrink::class.java)
}

fun mapJsonToUser(json: String?): User {
    val gson = Gson()
    return gson.fromJson(json, User::class.java)
}

fun mapJsonToIngredient(json: String?): Ingredient {
    val gson = Gson()
    return gson.fromJson(json, Ingredient::class.java)
}

fun mapJsonToIngredients(json: String?): List<Ingredient> {
    val gson = Gson()
    return gson.fromJson(json, Array<Ingredient>::class.java).toList()
}

fun mapJsonToToken(json: String?): LogToken {
    val gson = Gson()
    return gson.fromJson(json, LogToken::class.java)
}

fun mapJsonToImgResponse(json: String?): fb_image {
    val gson = Gson()
    return gson.fromJson(json, fb_image::class.java)
}
