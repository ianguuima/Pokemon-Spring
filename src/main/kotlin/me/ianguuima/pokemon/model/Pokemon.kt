package me.ianguuima.pokemon.model

import lombok.Builder
import org.springframework.data.annotation.Id
import javax.validation.constraints.NotEmpty


@Builder
data class Pokemon(
        @Id val id: Long,
        @field:NotEmpty(message = "The name of this pokemon can't be empty.")
        val name: String
)