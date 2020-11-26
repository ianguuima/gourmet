package me.ianguuima.gourmet.integration

import com.nhaarman.mockito_kotlin.any
import me.ianguuima.gourmet.models.Dish
import me.ianguuima.gourmet.repositories.DishRepository
import me.ianguuima.gourmet.services.DishService
import me.ianguuima.gourmet.services.SonicService
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
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono


@ExtendWith
@WebFluxTest
@Import(DishService::class, SonicService::class)
class DishControllerIT {

    @MockBean
    lateinit var dishRepository: DishRepository

    @Autowired
    lateinit var testClient: WebTestClient

    private val dish = DishCreator.createDish()

    @BeforeEach
    fun setup() {
        `when`(dishRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(dish))

        `when`(dishRepository.save(any()))
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
                .expectBody<ResponseStatusException>()
    }

    @Test
    @DisplayName("save should return created when create a dish")
    fun save_shouldReturnCreated_WhenCreateADish() {
        val dishToBeSaved = DishCreator.createDish()

        testClient
                .post()
                .uri("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dishToBeSaved))
                .exchange()
                .expectStatus().isCreated
                .expectBody<Dish>()
                .isEqualTo(dish)
    }

    @Test
    @DisplayName("update should return ok when update a dish")
    fun update_shouldReturnOk_WhenCreateADish() {
        testClient
                .put()
                .uri("/dish/{id}", 1)
                .body(BodyInserters.fromValue(dish))
                .exchange()
                .expectStatus().isOk
                .expectBody<Dish>()
                .isEqualTo(dish)
    }

    @Test
    @DisplayName("update should return ok when update a dish")
    fun delete_shouldReturnError_WhenDishNotExists() {
        `when`(dishRepository.findById(BDDMockito.anyLong()))
                .thenReturn(Mono.empty())

        testClient
                .delete()
                .uri("/dish/{id}", 1)
                .exchange()
                .expectStatus().isNotFound
                .expectBody<ResponseStatusException>()
    }


}