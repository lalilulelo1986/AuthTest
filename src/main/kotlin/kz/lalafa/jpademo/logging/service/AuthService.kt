package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.AuthenticationResponse
import kz.lalafa.jpademo.logging.UserInfo
import org.springframework.stereotype.Service

@Service
class AuthService(
        private val userService: UserService
) {

    fun login(login: String, password: String): AuthenticationResponse {

        val user = userService.getUserByLogin(login)
                .orElseThrow { throw Exception("User does not exist") }
        if (userService.isRegistered(user) && user.password == password) {
            // TODO login
        }

        throw IllegalArgumentException("Login or password invalid")
    }

    fun register(loggedUserLogin: String, userInfo: UserInfo): UserInfo {

        val user = userService.getUserByPhone(userInfo.phone)
                ?: throw Exception("User not found")

        if (user.login != loggedUserLogin)
            throw IllegalArgumentException("Not valid user")
        if (userService.isRegistered(user))
            throw Exception("Already registered")

        return userService.register(userInfo)
    }
}