package me.ianguuima.pokemon.model

import lombok.With
import org.springframework.data.annotation.Id
import javax.validation.constraints.NotEmpty


data class Pokemon(
        @With
        @Id val id: Long,
        @field:NotEmpty(message = "The name of this pokemon can't be empty.")
        val name: String
)