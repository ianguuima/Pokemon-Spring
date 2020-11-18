package me.ianguuima.pokemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokemonApplication

fun main(args: Array<String>) {
/*    BlockHound.install()*/
    runApplication<PokemonApplication>(*args)
}
