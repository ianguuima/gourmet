package me.ianguuima.gourmet.util

import me.ianguuima.gourmet.models.Dish

class DishCreator {

    companion object {

        fun createDish() : Dish {
            return Dish(0, "Lasanha", arrayListOf("Massa", "Tomate", "Gostosa"))
        }

        fun createValidDish() : Dish {
            return Dish(1, "Feijão Tropeiro", arrayListOf("Linguiça", "Feijão", "Couve"))
        }

    }

}