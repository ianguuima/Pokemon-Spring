package me.ianguuima.pokemon.service

import me.ianguuima.pokemon.model.Pokemon
import me.ianguuima.pokemon.repository.PokemonRepository
import me.ianguuima.pokemon.util.PokemonCreator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.BDDMockito.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.server.ResponseStatusException
import reactor.blockhound.BlockHound
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.util.concurrent.FutureTask

@ExtendWith(SpringExtension::class)

internal class PokemonServiceTest {

    @InjectMocks
    lateinit var pokemonService: PokemonService

    @Mock
    lateinit var pokemonRepository: PokemonRepository

    private val pokemon = PokemonCreator.createValidPokemon()

    companion object {

        @BeforeAll
        fun blockHoundSetup() {
            BlockHound.install()
        }

    }

    @BeforeEach
    fun setup() {
        `when`(pokemonRepository.findAll())
                .thenReturn(Flux.just(pokemon))

        `when`(pokemonRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(pokemon))

        `when`(pokemonRepository.save(PokemonCreator.createPokemonToBeSaved()))
                .thenReturn(Mono.just(pokemon))

        `when`(pokemonRepository.delete(ArgumentMatchers.any(Pokemon::class.java)))
                .thenReturn(Mono.empty())
    }

    @Test
    @DisplayName("find all return a flux of pokemon")
    fun findAll_returnFluxOfAnime_WhenSuccessful() {
        StepVerifier.create(pokemonService.getAll())
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

    @Test
    @DisplayName("getById returns Mono with anime when it exists")
    fun findById_returnMonoOfAnime_WhenSuccessful() {
        StepVerifier.create(pokemonService.get(1))
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

    @Test
    @DisplayName("getById returns Mono error when anime does not exist")
    fun findById_returnMonoError_WhenEmptyMonoIsReturned() {
        `when`(pokemonRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty())


        StepVerifier.create(pokemonService.get(1))
                .expectSubscription()
                .expectError(ResponseStatusException::class.java)
                .verify()
    }

    @Test
    @DisplayName("save a pokemon when successful")
    fun save_CreatesPokemon_WhenSuccessful() {
        val pokemonToBeSaved = PokemonCreator.createPokemonToBeSaved()

        StepVerifier.create(pokemonService.save(pokemonToBeSaved))
                .expectSubscription()
                .expectNext(pokemon)
                .verifyComplete()
    }

    @Test
    @DisplayName("delete removes the pokemon when successful")
    fun delete_RemovesPokemon_WhenSuccessful() {
        StepVerifier.create(pokemonService.delete(1))
                .expectSubscription()
                .verifyComplete();
    }

}