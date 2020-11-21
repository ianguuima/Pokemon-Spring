package me.ianguuima.pokemon.integration

import me.ianguuima.pokemon.model.Pokemon
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
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.server.ResponseStatusException
import reactor.blockhound.BlockHound
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


@ExtendWith(SpringExtension::class)
@WebFluxTest
@Import(PokemonService::class)
class AnimeControllerIT {

    @MockBean
    lateinit var pokemonRepository: PokemonRepository

    @Autowired
    lateinit var testClient: WebTestClient

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

        `when`(pokemonRepository.findById(anyLong()))
                .thenReturn(Mono.just(pokemon))
    }

    @Test
    @DisplayName("find all return a flux of pokemon")
    fun findAll_returnFluxOfAnime_WhenSuccessful() {
        testClient
                .get()
                .uri("/pokemon")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(pokemon.id)
                .jsonPath("$.[0].name").isEqualTo(pokemon.name)
    }

    @Test
    @DisplayName("find all return a flux of pokemon")
    fun listAll_Flavor2_ReturnFluxOfAnime_WhenSuccessful () {
        testClient
                .get()
                .uri("/pokemon")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(Pokemon::class.java)
                .hasSize(1)
                .contains(pokemon)
    }


    @Test
    @DisplayName("getById returns Mono with anime when it exists")
    fun findById_returnMonoOfAnime_WhenSuccessful() {
        testClient
                .get()
                .uri("/pokemon/{id}", 1)
                .exchange()
                .expectStatus().isOk
                .expectBody<Pokemon>()
                .isEqualTo(pokemon)
    }

    @Test
    @DisplayName("getById returns Mono error when anime does not exist")
    fun findById_returnMonoError_WhenEmptyMonoIsReturned() {
        `when`(pokemonRepository.findById(anyLong()))
                .thenReturn(Mono.empty())


        testClient
                .get()
                .uri("/pokemon/{id}", 1)
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
    }


    fun methodInKotlin() {
        println("this is a Kotlin method!")
    }


}