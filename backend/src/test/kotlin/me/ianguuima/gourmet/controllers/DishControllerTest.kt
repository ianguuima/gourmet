package me.ianguuima.gourmet.controllers

import me.ianguuima.gourmet.services.DishService
import me.ianguuima.gourmet.util.DishCreator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
internal class DishControllerTest {


    @InjectMocks
    lateinit var dishController: DishController

    @Mock
    lateinit var dishService: DishService

    private val dish = DishCreator.createValidDish()


    @BeforeEach
    fun setup() {
        `when`(dishService.search(0, 1))
                .thenReturn(Flux.just(dish))

        `when`(dishService.get(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(dish))
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
    @DisplayName("getById returns a Mono with anime when it exists")
    fun findById_returnMonoOfDishWhenSuccessful() {
        StepVerifier.create(dishController.get(1))
                .expectSubscription()
                .expectNext(dish)
                .verifyComplete()
    }


}