package me.ianguuima.gourmet.util

import me.ianguuima.gourmet.models.Dish

class DishCreator {

    companion object {

        fun createUpdatedDish() : Dish {
            return Dish(1, "Lasanha", arrayListOf("Queijo", "Macarrão", "Calabresa"))
        }

        fun createDish() : Dish {
            return Dish(2, "Feijão Tropeiro", arrayListOf("Linguiça", "Feijão", "Couve"))
        }

        fun createValidationErrorDish(): Dish {
            return Dish(3, "Tes", arrayListOf())
        }

    }

}