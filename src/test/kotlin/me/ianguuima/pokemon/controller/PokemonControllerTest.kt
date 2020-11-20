package me.ianguuima.pokemon.controller

import me.ianguuima.pokemon.model.Pokemon
import me.ianguuima.pokemon.service.PokemonService
import me.ianguuima.pokemon.util.PokemonCreator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.`when`
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.blockhound.BlockHound
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import com.nhaarman.mockito_kotlin.any

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
        `when`(pokemonService.getAll())
                .thenReturn(Flux.just(pokemon))

        `when`(pokemonService.get(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(pokemon))

        `when`(pokemonService.save(any()))
                .thenReturn(Mono.just(pokemon))
    }

    @Test
    @DisplayName("find all return a flux of pokemon")
    fun findAll_returnFluxOfAnime_WhenSuccessful() {
        StepVerifier.create(pokemonController.getAll())
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

    @Test
    @DisplayName("getById returns Mono with anime when it exists")
    fun findById_returnMonoOfAnime_WhenSuccessful() {
        StepVerifier.create(pokemonController.getById(1))
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

    @Test
    @DisplayName("save a pokemon when successful")
    fun save_CreatesPokemon_WhenSuccessful() {
        val pokemonToBeSaved = PokemonCreator.createPokemonToBeSaved()

        StepVerifier.create(pokemonController.save(pokemonToBeSaved))
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

}