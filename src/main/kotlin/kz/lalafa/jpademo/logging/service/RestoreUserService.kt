package kz.lalafa.jpademo.logging.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.IllegalArgumentException

@Service
class RestoreUserService {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var smsService: SmsService

    fun requestOtp(phone: String) {

        val user = userService.getUserByPhone(phone)
                .orElseThrow { throw Exception("User not found") }
        if (!userService.isRegistered(user))
            throw Exception("User with this phone not registered")
        if (userService.isOtpActual(user, 5, 300))
            throw Exception("Waiting for SMS")

        val otp = smsService.sendOtp(phone)
        userService.updateOtp(phone, otp)
    }

    fun validateOtp(otp: String, token: String) {

        val user = userService.getUserByToken(token)
                .orElseThrow { throw IllegalArgumentException("Can't restore. No such token") }

        if (userService.isRegistered(user) && otp == user.otp && token == user.token) {
            userService.invalidateOtp(user)
            TODO() //redirect to restore page
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid otp or token")
        }
    }
}