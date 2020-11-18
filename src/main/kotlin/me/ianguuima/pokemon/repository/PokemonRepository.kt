package me.ianguuima.pokemon.repository

import me.ianguuima.pokemon.model.Pokemon
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PokemonRepository : ReactiveCrudRepository<Pokemon, Long>{
}