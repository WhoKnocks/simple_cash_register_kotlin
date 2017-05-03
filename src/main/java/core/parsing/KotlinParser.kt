package core.parsing

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import core.FoodType
import core.MenuItem

/**
 * Created by GJ on 2/05/2017.
 */
class KotlinParser {

    val reader = InputReader()


    fun readFiles(): List<MenuItem> {
        var inventory = mutableListOf<MenuItem>()

        val mapper = ObjectMapper().registerModule(KotlinModule())

        for (type in FoodType.values()) {
            val file = reader.readFile(type)
            val state: Inventory = mapper.readValue(file)
            val menuItems: List<MenuItem> = state.menuItems
            menuItems.map { x -> x.type = type }
            inventory.addAll(menuItems)
        }
        return inventory

    }


    data class Inventory(val menuItems: List<MenuItem>)
}

fun main(args: Array<String>) {

    val reader = InputReader()
    var inventory = mutableListOf<MenuItem>()

    val mapper = ObjectMapper().registerModule(KotlinModule())

    for (type in FoodType.values()) {
        val state: KotlinParser.Inventory = mapper.readValue(reader.readFile(type))
        val menuItems: List<MenuItem> = state.menuItems
        menuItems.map { x -> x.type = type }
        inventory.addAll(menuItems)
    }
    println()

//    println("Hello, world!")
//    val mapper = ObjectMapper()
//
//    val fries = MenuItem("Frutten", 1.0, FoodType.MAIN)
//    val steak = MenuItem("Steak", 10.0, FoodType.MAIN)
//    val mussles = MenuItem("Mosselen", 10.0, FoodType.MAIN)
//
//    val icecream = MenuItem("Ijs", 5.0, FoodType.DESSERT)
//    val tiramisu = MenuItem("Tiramisu", 8.0, FoodType.DESSERT)
//
//    val duvel = MenuItem("Duvel", 2.5, FoodType.DRINK)
//    val cola = MenuItem("Cola", 2.0, FoodType.DRINK)
//
//    val inv = Inventory()
//    inv.menuItems = listOf(fries, steak, mussles, icecream, tiramisu, duvel, cola)
//
//    val writeValueAsString = mapper.writeValueAsString(inv)
//    println(writeValueAsString)
}