package ui

import core.Basket
import core.CashRegister
import core.FoodType
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.TextAlignment
import tornadofx.*


/**
 * Created by GJ on 26/03/2017.
 */
class MainView : View() {

    val controller: MyController by inject()

    val bottomView: BottomView by inject()
    val topView: CategoriesView by inject()
    val centerView: FoodItemsView by inject()

    override val root = borderpane {
        top = topView.root
        center = centerView.root
        bottom = bottomView.root
    }


    class TopView : View() {
        override val root = label("Top View") {}
    }

    class BottomView : View() {
        val controller: MyController by inject()
        override val root = label(controller.updateTotal().toString()) {
            bind(controller.totalCount)
        }

    }

    class CategoriesView : View() {
        override val root = hbox(10.0)

        init {
            with(root) {
                button(FoodType.MAIN.description) {
                    prefHeight = 75.0
                    prefWidth = 200.0
                    style {
                        fontSize = Dimension(1.5, Dimension.LinearUnits.em)
                    }
                }
                button(FoodType.DESSERT.description) {
                    prefHeight = 75.0
                    prefWidth = 200.0
                    style {
                        fontSize = Dimension(1.5, Dimension.LinearUnits.em)
                    }
                }
                button(FoodType.DRINK.description) {
                    prefHeight = 75.0
                    prefWidth = 200.0
                    style {
                        fontSize = Dimension(1.5, Dimension.LinearUnits.em)
                    }
                }
            }
        }
    }


    class FoodItemsView : View() {

        val controller: MyController by inject()

        override val root = gridpane() {
            vgap = 10.0
            hgap = 10.0
        }

        init {
            with(root) {
                row {
                    gridpaneConstraints {
                        vhGrow = Priority.ALWAYS
                    }
                    button() {
                        prefHeight = 100.0
                        prefWidth = 200.0

                        val vbox = vbox(10.0) {
                            add(label("Fritten") {
                                prefWidth = 200.0
                                alignment = Pos.CENTER
                                style {
                                    fontSize = Dimension(1.5, Dimension.LinearUnits.em)
                                }
                            })
                            add(label("€1.0") {
                                prefWidth = 200.0
                                alignment = Pos.CENTER
                            })
                        }
                        graphic = vbox

                        setOnAction {
                            controller.add(1.0)
                        }

                    }
                    button("Mosselen") {
                        prefHeight = 100.0
                        prefWidth = 200.0
                        style {
                            fontSize = Dimension(1.5, Dimension.LinearUnits.em)
                        }
                    }
                }


                row {
                    button("Steak") {
                        prefHeight = 100.0
                        prefWidth = 200.0

                        style {
                            fontSize = Dimension(1.5, Dimension.LinearUnits.em)
                        }
                    }
                }
            }
        }

    }


    class MyController : Controller() {

        //val cashRegister = CashRegister(Basket(), mutableListOf())

        var activeList = mutableListOf<Double>()

        var totalCount = SimpleDoubleProperty()

        fun add(prijs: Double) {
            activeList.add(prijs)
            updateTotal()
        }

        fun updateTotal() {
            totalCount.value = activeList.sum();
        }
    }

}