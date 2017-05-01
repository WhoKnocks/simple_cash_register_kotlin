package core

/**
 * Created by GJ on 26/03/2017.
 */
class MenuItem(var description: String, var price: Double, var type: FoodType) {

    override fun toString(): String {
        return "MenuItem(description='$description', price=$price, type=$type)\n"
    }
}