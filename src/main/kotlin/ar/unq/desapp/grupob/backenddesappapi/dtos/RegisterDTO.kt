package ar.unq.desapp.grupob.backenddesappapi.dtos

data class RegisterDTO(
    val name: String?,
    val surname: String?,
    val email: String?,
    val address: String?,
    val password: String?,
    val cvuMp: String?,
    val walletAddress: String?) {
}