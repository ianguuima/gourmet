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
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.lang.RuntimeException


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

        `when`(dishRepository.existsByNameIgnoreCase(any()))
                .thenReturn(Mono.just(false))
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
                .expectBody<RuntimeException>()
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
    }

    @Test
    @DisplayName("save should return conflict when exists dish with the same name")
    fun save_shouldReturnConflict_WhenExistsDishWithSameName() {
        val dishToBeSaved = DishCreator.createDish()

        `when`(dishRepository.existsByNameIgnoreCase(dishToBeSaved.name))
                .thenReturn(Mono.just(true))

        testClient
                .post()
                .uri("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dishToBeSaved))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody<RuntimeException>()
    }

    @Test
    @DisplayName("save should return error when wrong validation is provided")
    fun save_returnError_WhenWrongValidationProvided() {
        val dishToValidate = DishCreator.createValidationErrorDish()

        testClient.post()
                .uri("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dishToValidate))
                .exchange()
                .expectStatus().isBadRequest
                .expectBody<RuntimeException>()
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
    @DisplayName("delete should return ok when provide a valid dish")
    fun delete_shouldReturnOk_WhenDelete() {
        testClient.delete()
                .uri("/dish/{id}", 1)
                .exchange()
                .expectStatus().isOk
    }

    @Test
    @DisplayName("delete should return error when dish does not exist")
    fun delete_shouldReturnError_WhenDishNotExists() {
        `when`(dishRepository.findById(BDDMockito.anyLong()))
                .thenReturn(Mono.empty())

        testClient
                .delete()
                .uri("/dish/{id}", 1)
                .exchange()
                .expectStatus().isNotFound
                .expectBody<RuntimeException>()
    }


}