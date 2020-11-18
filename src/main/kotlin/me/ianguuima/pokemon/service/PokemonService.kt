package me.ianguuima.pokemon.service

import me.ianguuima.pokemon.model.Pokemon
import me.ianguuima.pokemon.repository.PokemonRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PokemonService(
        val pokemonRepository: PokemonRepository
) {

    fun get(id: Long): Mono<Pokemon> {
        return pokemonRepository.findById(id).switchIfEmpty(
                Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found!"))
        )
    }

    fun getAll(): Flux<Pokemon> {
        return pokemonRepository.findAll()
    }

    fun save(pokemon: Pokemon): Mono<Pokemon> {
        return pokemonRepository.save(pokemon)
    }

    fun update(pokemon: Pokemon): Mono<Void> {
        return get(pokemon.id)
                .flatMap { save(pokemon) }
                .then()
    }

    fun delete(id: Long): Mono<Void> {
        return get(id).flatMap { pokemonRepository.deleteById(id) }
    }


}