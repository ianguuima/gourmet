package me.ianguuima.gourmet.integration

import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.repositories.DishRepository
import me.ianguuima.gourmet.services.DishService
import me.ianguuima.gourmet.util.DishCreator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@ExtendWith
@WebFluxTest
@Import(DishService::class)
class DishControllerIT {

    @MockBean
    lateinit var dishRepository: DishRepository

    @Autowired
    lateinit var testClient: WebTestClient

    private val pokemon = DishCreator.createValidDish()

    @BeforeEach
    fun setup() {
        `when`(dishRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(pokemon))
    }

    @Test
    @DisplayName("should return a list of dish when parameters is successfully provided.")
    fun findAll_shouldReturnListOfDish() {
        testClient.get()
                .uri("/dish?page=0&size=5")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Dish::class.java)
    }

    @Test
    @DisplayName("should display bad request when provide a non number on dishController search parameters")
    fun findAll_expectBadRequestWhenProvideANonNumber() {
        testClient.get()
                .uri("/dish?page=a")
                .exchange()
                .expectStatus()
                .isBadRequest

        testClient.get()
                .uri("/dish?size=a")
                .exchange()
                .expectStatus()
                .isBadRequest

        testClient.get()
                .uri("/dish?page=a&size=a")
                .exchange()
                .expectStatus()
                .isBadRequest
    }

}