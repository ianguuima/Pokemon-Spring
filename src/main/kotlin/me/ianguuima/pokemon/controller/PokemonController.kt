package me.ianguuima.pokemon.controller

import me.ianguuima.pokemon.model.Pokemon
import me.ianguuima.pokemon.service.PokemonService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import javax.validation.Valid

@RestController
@CrossOrigin
@RequestMapping(value = ["pokemon"])
class PokemonController(
        val pokemonService: PokemonService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): Flux<Pokemon> {
        return pokemonService.getAll()
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getById(@PathVariable id: Long): Mono<Pokemon> {
        return pokemonService.get(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody @Valid pokemon: Pokemon): Mono<Pokemon> {
        return pokemonService.save(pokemon)
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id : Long, @RequestBody @Valid pokemon: Pokemon) : Mono<Pokemon> {
        return pokemonService.update(pokemon.copy(id = id))
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun delete(@PathVariable id : Long) : Mono<Void> {
        return pokemonService.delete(id)
    }

}