package core


/**
 * Created by GJ on 26/03/2017.
 */
fun main(args: Array<String>) {
    val fries = MenuItem("Fries", 1.0, FoodType.MAIN)
    val steak = MenuItem("Steak", 10.0, FoodType.MAIN)
    val mussles = MenuItem("Mussles", 10.0, FoodType.MAIN)

    val icecream = MenuItem("Icecream", 5.0, FoodType.DESSERT)
    val tiramisu = MenuItem("Tiramisu", 8.0, FoodType.DESSERT)

    val duvel = MenuItem("Duvel", 2.5, FoodType.DRINK)
    val cola = MenuItem("Cola", 2.0, FoodType.DRINK)

    val basket = Basket()
    basket.selectedItems.add(fries)
    basket.selectedItems.add(steak)
    basket.selectedItems.add(mussles)

    basket.selectedItems.add(icecream)
    basket.selectedItems.add(tiramisu)

    basket.selectedItems.add(duvel)
    basket.selectedItems.add(cola)


    println(basket.calcPriceByType(FoodType.MAIN))

}