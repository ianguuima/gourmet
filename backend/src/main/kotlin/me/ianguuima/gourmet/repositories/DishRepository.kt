package me.ianguuima.gourmet.repositories

import me.ianguuima.gourmet.models.Dish
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Repository
interface DishRepository : ReactiveSortingRepository<Dish, Long> {

    fun findByIdNotNull(pageable: Pageable): Flux<Dish>

    fun existsByNameIgnoreCase(name : String) : Mono<Boolean>
}