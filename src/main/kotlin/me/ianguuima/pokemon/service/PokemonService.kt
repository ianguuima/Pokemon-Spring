package me.ianguuima.pokemon.service

import me.ianguuima.pokemon.model.Pokemon
import me.ianguuima.pokemon.repository.PokemonRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class PokemonService(
        val pokemonRepository: PokemonRepository
) {

    companion object {
        const val cacheName = "Pokemon"
    }

    @Cacheable(cacheNames = [cacheName], key = "#root.method.name")
    fun getAll(): Flux<Pokemon> {
        return pokemonRepository.findAll().cache(Duration.ofSeconds(5))
    }

    @Cacheable(cacheNames = [cacheName], key = "#id")
    fun get(id: Long): Mono<Pokemon> {
        return pokemonRepository.findById(id).switchIfEmpty(
                Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon not found!"))
        ).cache(Duration.ofSeconds(5))
    }

    @CacheEvict(cacheNames = [cacheName], allEntries = true)
    fun save(pokemon: Pokemon): Mono<Pokemon> {
        return pokemonRepository.save(pokemon)
    }

    @CachePut(cacheNames = [cacheName], key = "#pokemon.id")
    fun update(pokemon: Pokemon): Mono<Pokemon> {
        return get(pokemon.id)
                .flatMap { save(pokemon) }
    }

    @CacheEvict(cacheNames = [cacheName], key = "#id")
    fun delete(id: Long): Mono<Void> {
        return get(id).flatMap { pokemonRepository.deleteById(id) }
    }


}