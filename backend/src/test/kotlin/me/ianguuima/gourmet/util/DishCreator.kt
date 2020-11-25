package me.ianguuima.gourmet.util

import me.ianguuima.gourmet.models.Dish

class DishCreator {

    companion object {

        fun createDish() : Dish {
            return Dish(1, "Feijão Tropeiro", arrayListOf("Linguiça", "Feijão", "Couve"))
        }

        fun createUpdatedDish() : Dish {
            return Dish(1, "Lasanha", arrayListOf("Queijo", "Macarrão", "Calabresa"))
        }

    }

}