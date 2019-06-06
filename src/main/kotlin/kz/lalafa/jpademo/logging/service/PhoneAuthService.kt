package kz.lalafa.jpademo.logging.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PhoneAuthService : OtpInterface {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var smsService: SmsService

    override fun requestOtp(phone: String) {

        userService.getUserByPhone(phone)?.let {
            if (userService.isRegistered(it))
                throw Exception("User already registered")
            if (userService.isOtpActual(it))
                throw Exception("Waiting for SMS")
        }

        val otp = smsService.sendOtp(phone)
        userService.assignOtp(phone, otp, "token")
    }

    override fun validateOtp(otp: String, token: String) {

        val user = userService.getUserByToken(token)
                ?: throw IllegalArgumentException("Can't login. No such token")

        if (userService.isRegistered(user))
            throw Exception("user already registered. Restore your password.")

        if (userService.isOtpActual(user) && otp == user.otp && token == user.token) {
            userService.invalidateOtp(user)
            TODO() //redirect to logged page
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid otp or token")
        }
    }
}