package me.ianguuima.gourmet.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table
data class Dish(@Id val id : Long, val name : String, val ingredients : List<String>)