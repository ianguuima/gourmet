package me.ianguuima.gourmet.integration

import me.ianguuima.gourmet.exceptions.DishNotFoundException
import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.repositories.DishRepository
import me.ianguuima.gourmet.services.DishService
import me.ianguuima.gourmet.util.DishCreator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.BDDMockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono


@ExtendWith
@WebFluxTest
@Import(DishService::class)
class DishControllerIT {

    @MockBean
    lateinit var dishRepository: DishRepository

    @Autowired
    lateinit var testClient: WebTestClient

    private val dish = DishCreator.createValidDish()

    @BeforeEach
    fun setup() {
        `when`(dishRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(dish))
    }

    @Test
    @DisplayName("findAll should return a list of dish when parameters is successfully provided.")
    fun findAll_shouldReturnListOfDish() {
        testClient.get()
                .uri("/dish?page=0&size=5")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Dish::class.java)
    }

    @Test
    @DisplayName("findAll should display bad request when provide a non number on dishController search parameters")
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

    @Test
    @DisplayName("getById should return a dish when param")
    fun getById_shouldReturnADish() {
        testClient
                .get()
                .uri("/dish/{id}", 1)
                .exchange()
                .expectStatus().isOk
                .expectBody<Dish>()
                .isEqualTo(dish)
    }


    @Test
    @DisplayName("getById should return error when it doesn't exist")
    fun getById_returnError_WhenDishNotExists() {
        `when`(dishRepository.findById(BDDMockito.anyLong()))
                .thenReturn(Mono.empty())

        testClient
                .get()
                .uri("/dish/{id}", 1)
                .exchange()
                .expectStatus().isNotFound
                .expectBody<DishNotFoundException>()
    }

}