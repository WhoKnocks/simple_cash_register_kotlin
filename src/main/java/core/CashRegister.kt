package core

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;


/**
 * Created by GJ on 26/03/2017.
 */
class CashRegister(var activeBasket: Basket, var basketHistory: MutableList<Basket>) {

    var logger = Logger.getLogger(CashRegister::class.java)

    var stock: List<MenuItem>

    init {
        val fries = MenuItem("Fries", 1.0, FoodType.MAIN)
        val steak = MenuItem("Steak", 10.0, FoodType.MAIN)
        val mussles = MenuItem("Mussles", 10.0, FoodType.MAIN)

        val icecream = MenuItem("Icecream", 5.0, FoodType.DESSERT)
        val tiramisu = MenuItem("Tiramisu", 8.0, FoodType.DESSERT)

        val duvel = MenuItem("Duvel", 2.5, FoodType.DRINK)
        val cola = MenuItem("Cola", 2.0, FoodType.DRINK)


        stock = listOf(fries, steak, mussles, icecream, tiramisu, duvel, cola)

    }

    fun addToBasket(item: MenuItem) {
      //  activeBasket.selectedItems.add(item);

    }


}