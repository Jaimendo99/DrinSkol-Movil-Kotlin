package drinskol.app.drinksolv1.models

data class Ingredient(
    val id: Int,
    val name: String,
    val description: String,
    val img_src: String,
    val alcohol_porcentage: Float
)
