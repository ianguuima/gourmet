package me.ianguuima.gourmet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GourmetApplication

fun main(args: Array<String>) {
	runApplication<GourmetApplication>(*args)
}
