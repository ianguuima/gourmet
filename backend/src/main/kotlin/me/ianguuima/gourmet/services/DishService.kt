package me.ianguuima.gourmet.services

import me.ianguuima.gourmet.exceptions.DishNotFoundException
import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.repositories.DishRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
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
                    Mono.error(DishNotFoundException())
            ).cache()


    @CacheEvict(cacheNames = [cacheName], key = "#dish.id")
    fun save(dish: Dish): Mono<Dish> {
        return dishRepository.save(dish).flatMap {
            sonicService.add(it.id, "${it.name}  ${it.ingredients.joinToString(separator = " ")}")
            Mono.just(it)
        }
    }

    @CachePut(cacheNames = [cacheName], key = "#dish.id")
    fun update(dish: Dish): Mono<Dish> = get(dish.id).flatMap { dishRepository.save(dish) }

    @CacheEvict(cacheNames = [cacheName], key = "#id")
    fun delete(id: Long): Mono<Void> = get(id).flatMap { dishRepository.deleteById(it.id) }
}