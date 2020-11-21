package me.ianguuima.pokemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class PokemonApplication

fun main(args: Array<String>) {
/*    BlockHound.install()*/
    runApplication<PokemonApplication>(*args)
}
