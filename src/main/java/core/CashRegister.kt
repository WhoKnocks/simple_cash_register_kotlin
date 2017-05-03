package core

import core.parsing.KotlinParser
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

        val duvel = MenuItem("Duvel", 2.5, FoodType.ALCOHOL)
        val Westmalle = MenuItem("Westmalle", 2.5, FoodType.ALCOHOL)

        val fanta = MenuItem("Fanta", 2.0, FoodType.DRINK)
        val cola = MenuItem("Col²a", 2.0, FoodType.DRINK)

        val choco = MenuItem("Choco", 2.0, FoodType.HOT_DRINK)
        val koffie = MenuItem("Koffie", 2.0, FoodType.HOT_DRINK)

        val zChips = MenuItem("Zoute chips", 1.0, FoodType.SNACK)
        val pChips = MenuItem("Paprika chips", 1.0, FoodType.SNACK)

        val soep = MenuItem("Soep", 1.0, FoodType.PRE)

        val parser = KotlinParser()
        stock = parser.readFiles()

        //stock = listOf(fries, steak, mussles, icecream, tiramisu, duvel, Westmalle, cola, fanta, choco, koffie, zChips, pChips, soep)

    }

    fun addToBasket(item: MenuItem) {
        //  activeBasket.selectedItems.add(item);

    }


}