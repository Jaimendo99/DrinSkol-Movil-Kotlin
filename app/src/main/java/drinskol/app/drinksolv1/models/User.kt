package drinskol.app.drinksolv1.models

data class User(
    val id:Int,
    val name:String,
    val lastname:String,
    val email:String,
    val username:String,
    val img_src:String,
    val birth_date: String,
    val drinks : List<Drink>,
    val is_admin:Boolean,
)

data class logUser(
    val username:String,
    val password:String,
)

data class SignUpUser(
    val password: String,
    val last_name: String,
    val username: String,
    val birth_date: String,
    val image_src: String,
    val name: String,
    val email: String,
    val is_admin: Boolean
)

