package kz.lalafa.jpademo.logging.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.IllegalArgumentException

@Service
class PhoneAuthService : OtpInterface {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var smsService: SmsService

    override fun requestOtp(phone: String) {

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

    override fun validateOtp(otp: String, token: String) {

        val user = userService.getUserByToken(token)
                .orElseThrow { throw IllegalArgumentException("Can't login. No such token") }

        if (userService.isRegistered(user))
            throw Exception("user already registered. Restore your password.")

        if (userService.isOtpActual(user, 5, 300) && otp == user.otp && token == user.token) {
            userService.invalidateOtp(user)
            TODO() //redirect to login page
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid otp or token")
        }
    }
}