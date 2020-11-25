package me.ianguuima.gourmet.services

import me.ianguuima.gourmet.exceptions.DishNotFoundException
import me.ianguuima.gourmet.repositories.DishRepository
import me.ianguuima.gourmet.util.DishCreator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
internal class DishServiceTest {

    @InjectMocks
    lateinit var dishService: DishService

    @Mock
    lateinit var sonicService: SonicService

    @Mock
    lateinit var dishRepository: DishRepository

    private val dish = DishCreator.createDish()

    @BeforeEach
    fun setup() {
        `when`(dishRepository.findByIdNotNull(PageRequest.of(0, 5)))
                .thenReturn(Flux.just(dish))

        `when`(dishRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(dish))

        `when`(dishRepository.save(dish))
                .thenReturn(Mono.just(dish))

        val updatedDish = DishCreator.createUpdatedDish()

        `when`(dishRepository.save(updatedDish))
                .thenReturn(Mono.just(updatedDish))

        `when`(dishRepository.deleteById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty())
    }

    @Test
    @DisplayName("find all return a flux of dish")
    fun findAll_returnFluxOfDish_WhenSuccessful() {
        StepVerifier.create(dishService.search(0, 5))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }

    @Test
    @DisplayName("getById should return a mono of dish")
    fun getById_returnMonoOfDish_WhenSuccessful() {
        StepVerifier.create(dishService.get(1))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }

    @Test
    @DisplayName("getById should return error if dish doesn't exist")
    fun getById_returnError_WhenDishNotExists() {
        `when`(dishRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty())


        StepVerifier.create(dishService.get(1))
                .expectSubscription()
                .expectError(DishNotFoundException::class.java)
                .verify()
    }

    @Test
    @DisplayName("save a dish when successful")
    fun save_createDish_whenSuccessful() {
        StepVerifier.create(dishService.save(dish))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }

    @Test
    @DisplayName("save updated dish and returns empty mono when successful")
    fun update_saveUpdatedDish_whenSuccessful() {
        val updatedDish = DishCreator.createUpdatedDish()

        StepVerifier.create(dishService.update(updatedDish))
                .expectSubscription()
                .expectNext(updatedDish)
                .verifyComplete()
    }


    @Test
    @DisplayName("update returns Mono error when dish does not exist")
    fun update_ReturnMonoError_WhenEmptyMonoIsReturned() {
        `when`(dishService.get(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty())

        StepVerifier.create(dishService.update(DishCreator.createUpdatedDish()))
                .expectSubscription()
                .expectError(DishNotFoundException::class.java)
                .verify()
    }


    @Test
    @DisplayName("delete removes the dish when successful")
    fun delete_RemovesPokemon_WhenSuccessful() {
        StepVerifier.create(dishService.delete(1))
                .expectSubscription()
                .verifyComplete()
    }



}