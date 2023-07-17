package drinskol.app.drinksolv1.models

data class Recipe(
    val id:Int,
    val description:String,
    val order:Int,
    val quantity:Float,
    val unit:String,
    val drink_id:Int,
    val ingredient : Ingredient
)


