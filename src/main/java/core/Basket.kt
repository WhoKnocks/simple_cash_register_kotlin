package core

/**
 * Created by GJ on 26/03/2017.
 */
class Basket(var selectedItems: MutableList<SelectedMenuItem> = mutableListOf<SelectedMenuItem>()) {

    fun calcPriceByType(foodType: FoodType): Double {
        return selectedItems.filter { x -> x.item.type == foodType }.sumByDouble { x -> (x.item.price) * x.amount }
    }

    fun calcTotalPrice(): Double {
        return selectedItems.sumByDouble { x -> x.item.price * x.amount }
    }

    override fun toString(): String {
        return "Basket(selectedItems=$selectedItems)\n"
    }

    class SelectedMenuItem(var item: MenuItem, var amount: Int) {

        fun addMenuItem(){
            amount++
        }

        fun removeMenuItem(){
            amount--
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other?.javaClass != javaClass) return false

            other as SelectedMenuItem

            if (item != other.item) return false

            return true
        }

        override fun hashCode(): Int {
            return item.hashCode()
        }
    }


}