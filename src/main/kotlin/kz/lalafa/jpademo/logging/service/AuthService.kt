package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.UserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.IllegalArgumentException

@Service
class AuthService {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var smsService: SmsService

    fun login(login: String, password: String) {

        val user = userService.getUserByLogin(login).orElseThrow { throw Exception("User does not exist") }
        if (userService.isRegistered(user) && user.password == password) {
            // TODO login
        }

        throw IllegalArgumentException("Login or password invalid")
    }

    fun requestLoggingByPhone(phone: String) {

        val user = userService.getUserByPhone(phone)
        user.ifPresent {
            if (userService.isRegistered(it))
                throw Exception("User already registered")
            if (userService.isOtpActual(it, 5, 300))
                throw Exception("Waiting for SMS")
        }

        val otp = smsService.sendOtp(phone)
        userService.updateOtp(phone, otp)
    }

    fun requestRestoreByPhone(phone: String) {

        val user = userService.getUserByPhone(phone).orElseThrow { throw Exception("User not found") }
        if (!userService.isRegistered(user))
            throw Exception("User with this phone not registered")
        if (userService.isOtpActual(user, 5, 300))
            throw Exception("Waiting for SMS")

        val otp = smsService.sendOtp(user.phone)
        userService.updateOtp(phone, otp)
    }

    fun registerLoggedUser(loggedUserLogin: String, userInfo: UserInfo) {

        val user = userService.getUserByPhone(userInfo.phone).orElseThrow { throw Exception("User not found") }
        if (user.login != loggedUserLogin)
                throw IllegalArgumentException("Not valid user")
        if (userService.isRegistered(user))
                throw Exception("Already registered")

        userService.register(userInfo)
    }

    fun validateOtp(otp: String, token: String) {

    }
}