package ar.unq.desapp.grupob.backenddesappapi.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description= "Model for registered user entry.")
data class RegisterDTO(val username: String, val password: String) {
}