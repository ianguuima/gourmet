package me.ianguuima.gourmet.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class DishNotFoundException(override val message: String? = "Dish not found!") : RuntimeException()