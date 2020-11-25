package me.ianguuima.gourmet.config

import me.ianguuima.gourmet.interpolator.ValidationMessageInterpolator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import javax.validation.Validation

@Configuration
class WebConfig : WebFluxConfigurer {

    @Bean
    fun registerMessageInterpolator() {
        Validation.byDefaultProvider().configure().messageInterpolator(
                ValidationMessageInterpolator(
                        Validation.byDefaultProvider().configure().defaultMessageInterpolator
                )
        )
    }

}