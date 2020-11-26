package me.ianguuima.gourmet.repositories

import me.ianguuima.gourmet.models.Dish
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface DishRepository : ReactiveSortingRepository<Dish, Long> {

    fun findByIdNotNull(pageable: Pageable): Flux<Dish>

}