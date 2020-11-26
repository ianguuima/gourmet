package me.ianguuima.gourmet.controllers

import io.swagger.annotations.Api
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.services.DishService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.validation.Valid

@RestControllerAdvice
@CrossOrigin
@RequestMapping(value = ["/dish"])
@Api(tags = ["Dish"])
class DishController(
        val dishService: DishService,
) {

    @Operation(
            summary = "Get dish by id",
    )
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Mono<Dish> {
        return dishService.get(id)
    }

    @Operation(
            summary = "Get all dishes",
    )
    @GetMapping
    fun findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
            @RequestParam(value = "size", required = false, defaultValue = "5") size: Int
    ): Flux<Dish> {
        return dishService.search(page, size)
    }

    @Operation(
            summary = "Get word suggestions",
            description = "This method will search by related words given initial words. Only en-us supported!"
    )
    @GetMapping("/suggest")
    fun suggest(
            @RequestParam(value = "term", required = true) term : String,
    ): ArrayList<String> {
        return dishService.sonicService.suggest(term)
    }

    @Operation(
            summary = "Search dishes",
            description = "You can search for an ingredient." +
                    " Then, this method will return dishes id that contains this ingredient."
    )
    @GetMapping("/search")
    fun search(
            @RequestParam(value = "term", required = true) term : String,
    ): ArrayList<String> {
        return dishService.sonicService.query(term)
    }


    @Operation(
            summary = "Create a new dish",
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody @Valid dish: Dish): Mono<Dish> {
        return dishService.save(dish)
    }


    @Operation(
            summary = "Update a dish",
    )
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid dish: Dish): Mono<Dish> {
        return dishService.update(dish.copy(id = id))
    }

    @Operation(
            summary = "Delete a dish",
    )
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> {
        return dishService.delete(id)
    }


}