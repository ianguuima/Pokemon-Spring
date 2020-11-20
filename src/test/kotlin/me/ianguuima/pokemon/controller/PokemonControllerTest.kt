package me.ianguuima.pokemon.controller

import me.ianguuima.pokemon.repository.PokemonRepository
import me.ianguuima.pokemon.service.PokemonService
import me.ianguuima.pokemon.util.PokemonCreator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.blockhound.BlockHound
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@ExtendWith(SpringExtension::class)
internal class PokemonControllerTest {

    @InjectMocks
    lateinit var pokemonController: PokemonController

    @Mock
    lateinit var pokemonService: PokemonService

    private val pokemon = PokemonCreator.createValidPokemon()

    companion object {

        @BeforeAll
        fun blockHoundSetup() {
            BlockHound.install()
        }

    }

    @BeforeEach
    fun setup() {
        BDDMockito.`when`(pokemonService.getAll())
                .thenReturn(Flux.just(pokemon))
    }

    @Test
    @DisplayName("find all return a flux of pokemon")
    fun findAll_returnFluxOfAnime_WhenSuccessful() {
        StepVerifier.create(pokemonController.getAll())
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

}