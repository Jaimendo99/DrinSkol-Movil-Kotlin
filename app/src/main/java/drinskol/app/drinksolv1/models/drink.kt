package drinskol.app.drinksolv1.models



data class Drink(
    val id: Int,
    val name: String,
    val description: String,
    val img_src: String,
    val alcohol_porcentage: Float
)
data class DetailsDrink(
    val id: Int,
    val name: String,
    val description: String,
    val img_src: String,
    val alcohol_percentage: Float,
    val recipes: List<Recipe>,
    val tags: List<Tags>
)