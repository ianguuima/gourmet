package me.ianguuima.gourmet.controllers

import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.services.DishService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.ArrayList
import javax.validation.Valid

@RestController
@CrossOrigin
@RequestMapping(value = ["/dish"])
class DishController(
        val dishService: DishService,
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Mono<Dish> {
        return dishService.get(id)
    }

    @GetMapping
    fun findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") page: Int,
            @RequestParam(value = "size", required = false, defaultValue = "5") size: Int
    ): Flux<Dish> {
        return dishService.search(page, size)
    }

    @GetMapping("/suggest")
    fun suggest(
            @RequestParam(value = "term", required = true) term : String,
    ): ArrayList<String> {
        return dishService.sonicService.suggest(term)
    }

    @GetMapping("/search")
    fun search(
            @RequestParam(value = "term", required = true) term : String,
    ): ArrayList<String> {
        return dishService.sonicService.query(term)
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody @Valid dish: Dish): Mono<Dish> {
        return dishService.save(dish)
    }


    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody @Valid dish: Dish): Mono<Dish> {
        return dishService.update(dish.copy(id = id))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Mono<Void> {
        return dishService.delete(id)
    }


}