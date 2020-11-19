package me.ianguuima.pokemon.util

import me.ianguuima.pokemon.model.Pokemon

class PokemonCreator {

    companion object {

        fun createPokemonToBeSaved(): Pokemon {
            return Pokemon(0, "Bulbasaur")
        }


        fun createValidPokemon(): Pokemon {
            return Pokemon(id = 1, name = "Bulbasaur")
        }

        fun createValidUpdatedPokemon() : Pokemon {
            return Pokemon(id = 1, name = "Venosaur")
        }


    }

}