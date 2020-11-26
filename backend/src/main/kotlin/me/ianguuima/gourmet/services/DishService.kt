package me.ianguuima.gourmet.services

import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.repositories.DishRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class DishService(
        val dishRepository: DishRepository,
        val sonicService: SonicService
) {

    companion object {
        const val cacheName = "Dish"
    }

    fun search(page: Int, size: Int): Flux<Dish> {
        val pageRequest = PageRequest.of(
                page,
                size
        )

        return dishRepository.findByIdNotNull(pageRequest)
    }

    @Cacheable(cacheNames = [cacheName], key = "#id")
    fun get(id: Long): Mono<Dish> = dishRepository
            .findById(id)
            .switchIfEmpty(
                    Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND))
            ).cache()


    fun save(dish: Dish): Mono<Dish> {
        return dishRepository.existsByNameIgnoreCase(dish.name)
                .flatMap {
                    if (it) Mono.error(ResponseStatusException(HttpStatus.CONFLICT))
                    else {

                        dishRepository.save(dish).flatMap { createdDish ->
                            sonicService.add(
                                    createdDish.id,
                                    "${createdDish.name}  ${createdDish.ingredients.joinToString(separator = " ")}"
                            )
                            Mono.just(createdDish)
                        }

                    }
                }
    }

    @CachePut(cacheNames = [cacheName], key = "#dish.id")
    fun update(dish: Dish): Mono<Dish> {
        return get(dish.id).flatMap { dishRepository.save(dish) }.flatMap {
            sonicService.add(it.id, "${it.name}  ${it.ingredients.joinToString(separator = " ")}")
            Mono.just(it)
        }
    }

    @CacheEvict(cacheNames = [cacheName], key = "#id")
    fun delete(id: Long): Mono<Void> {
        return get(id).flatMap {
            dishRepository.deleteById(it.id)
            sonicService.remove(it.id)
            Mono.empty()
        }
    }
}