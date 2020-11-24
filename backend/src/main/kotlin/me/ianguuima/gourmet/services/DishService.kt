package me.ianguuima.gourmet.services

import me.ianguuima.gourmet.exceptions.DishNotFoundException
import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.repositories.DishRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DishService(
        val dishRepository: DishRepository
) {

    fun search(page : Int, size : Int): Flux<Dish> {
        val pageRequest = PageRequest.of(
                page,
                size
        )

        return dishRepository.findByIdNotNull(pageRequest)
    }

    fun get(id : Long) : Mono<Dish> {
        return dishRepository.findById(id).switchIfEmpty(
            Mono.error(DishNotFoundException())
        )
    }


    fun save(dish : Dish): Mono<Dish> {
        return dishRepository.save(dish)
    }

    fun update(dish : Dish): Mono<Dish> {
        return get(dish.id).flatMap {
            dishRepository.save(dish)
        }
    }

    fun delete(id : Long): Mono<Void> {
        return get(id).flatMap { dishRepository.delete(it) }
    }

}