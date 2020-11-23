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

    @BeforeEach
    fun setup() {
        `when`(pokemonService.getAll())
                .thenReturn(Flux.just(pokemon))

        `when`(pokemonService.get(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(pokemon))

        `when`(pokemonService.save(any()))
                .thenReturn(Mono.just(pokemon))

        `when`(pokemonService.delete(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty())

        `when`(pokemonService.update(PokemonCreator.createValidUpdatedPokemon()))
                .thenReturn(Mono.empty())
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

    @Test
    @DisplayName("delete removes the pokemon when successful")
    fun delete_RemovesPokemon_WhenSuccessful() {
        StepVerifier.create(pokemonController.delete(1))
                .expectSubscription()
                .verifyComplete()
    }

    @Test
    @DisplayName("update save updated pokemon and returns empty mono when successful")
    fun update_saveUpdatedPokemon_WhenSuccessful() {
        StepVerifier.create(pokemonController.update(1, PokemonCreator.createValidUpdatedPokemon()))
                .expectSubscription()
                .verifyComplete()
    }


}