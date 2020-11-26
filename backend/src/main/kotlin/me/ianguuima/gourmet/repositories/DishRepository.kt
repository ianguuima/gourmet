package me.ianguuima.gourmet.repositories

import me.ianguuima.gourmet.models.Dish
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Repository
interface DishRepository : ReactiveSortingRepository<Dish, Long> {

    fun findByIdNotNull(pageable: Pageable): Flux<Dish>

    fun existsByName(@NotEmpty(message = "The name must not be empty")
                     @Size(max = 20, message = "The name must have between 3 and 20 characters", min = 3)
                     name: String) : Mono<Boolean>
}