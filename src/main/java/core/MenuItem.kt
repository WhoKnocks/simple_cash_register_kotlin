package core

/**
 * Created by GJ on 26/03/2017.
 */
class MenuItem(var description: String, var price: Double) {

    lateinit var type: FoodType

    constructor(description: String, price: Double, type: FoodType) : this(description, price) {
        this.type = type
    }

    override fun toString(): String {
        return "MenuItem(description='$description', price=$price, type=$type)\n"
    }
}