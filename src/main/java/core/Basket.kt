package core

/**
 * Created by GJ on 26/03/2017.
 */
class Basket(var selectedItems: MutableList<MenuItem> = mutableListOf<MenuItem>()) {

    fun calcPriceByType(foodType: FoodType): Double {
        return selectedItems.filter { x -> x.type == foodType }.sumByDouble { x -> x.price }
    }

    fun calcTotalPrice(): Double {
        return selectedItems.sumByDouble { x -> x.price }
    }
}