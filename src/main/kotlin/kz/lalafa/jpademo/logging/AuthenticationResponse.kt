package kz.lalafa.jpademo.logging

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String,
    val roles: List<String> = emptyList()
)
