package me.ianguuima.gourmet.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size


@Table
data class Dish(
        @Id
        val id: Long,

        @field:NotEmpty(message = "The name must not be empty")
        @field:Size(message = "The name must have between 3 and 20 characters", min = 3, max = 20)
        val name: String,

        @field:NotEmpty(message = "The ingredients can't be empty.")
        val ingredients: List<@Size(message = "The ingredient must have between 3 and 20 characters", min = 3, max = 20) String>
)