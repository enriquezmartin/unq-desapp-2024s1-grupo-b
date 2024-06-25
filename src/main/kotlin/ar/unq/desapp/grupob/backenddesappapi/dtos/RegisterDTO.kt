package ar.unq.desapp.grupob.backenddesappapi.dtos

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "User registration parameters.")
data class RegisterDTO(
    @Schema(description = "User name", example = "Homero")
    val name: String?,
    @Schema(description = "User surname", example = "Simpson")
    val surname: String?,
    @Schema(description = "User email", example = "a_valid@email.com")
    val email: String?,
    @Schema(description = "User address", example = "Evergreen Avenue 742")
    val address: String?,
    @Schema(description = "User password", example = "P@ssW0Rd")
    val password: String?,
    @Schema(description = "User CVU, 22 digits", example = "0229037489012372348956")
    val cvuMp: String?,
    @Schema(description = "User Wallet Address, 8 digits", example = "12345678")
    val walletAddress: String?) {
}