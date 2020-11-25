package me.ianguuima.gourmet.controllers

import com.nhaarman.mockito_kotlin.any
import me.ianguuima.gourmet.services.DishService
import me.ianguuima.gourmet.services.SonicService
import me.ianguuima.gourmet.util.DishCreator
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.`when`
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
@Import(SonicService::class)
internal class DishControllerTest {


    @InjectMocks
    lateinit var dishController: DishController

    @Mock
    lateinit var dishService: DishService

    private val dish = DishCreator.createDish()


    @BeforeEach
    fun setup() {
        `when`(dishService.search(0, 1))
                .thenReturn(Flux.just(dish))

        `when`(dishService.get(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(dish))

        `when`(dishService.save(any()))
                .thenReturn(Mono.just(dish))

        `when`(dishService.update(dish))
                .thenReturn(Mono.empty())

        `when`(dishService.delete(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty())
    }


    @Test
    @DisplayName("find all return a flux of dish")
    fun findAll_returnFluxOfDish_WhenSuccessful() {
        StepVerifier.create(dishController.findAll(0, 1))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }

    @Test
    @DisplayName("getById returns a Mono with dish when it exists")
    fun getById_returnMonoOfDishWhenSuccessful() {
        StepVerifier.create(dishController.get(1))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }

    @Test
    @DisplayName("save a dish when successful")
    fun save_createDish_whenSuccessful() {
        val dishToBeSaved = DishCreator.createDish()

        StepVerifier.create(dishController.save(dishToBeSaved))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }

    @Test
    @DisplayName("save updated dish when successful")
    fun update_saveUpdatedDish_WhenSuccessful() {
        StepVerifier.create(dishController.update(1, DishCreator.createDish()))
                .expectSubscription()
                .verifyComplete()
    }

    @Test
    @DisplayName("delete dish when successful")
    fun delete_removesDish_WhenSuccessful() {
        StepVerifier.create(dishController.delete(1))
                .expectSubscription()
                .verifyComplete()
    }

}