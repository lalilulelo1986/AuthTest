package kz.lalafa.jpademo.logging.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RestoreUserService : OtpInterface {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var smsService: SmsService

    override fun requestOtp(phone: String) {

        val user = userService.getUserByPhone(phone)
                ?: throw Exception("User not found")

        if (!userService.isRegistered(user))
            throw Exception("User with this phone not registered")
        if (userService.isOtpActual(user))
            throw Exception("Waiting for SMS")

        val otp = smsService.sendOtp(phone)
        userService.assignOtp(phone, otp, "token")
    }

    override fun validateOtp(otp: String, token: String) {

        val user = userService.getUserByToken(token)
                ?: throw IllegalArgumentException("Can't restore. No such token")

        if (!userService.isRegistered(user))
            throw Exception("User not registered")

        if (userService.isOtpActual(user) && otp == user.otp && token == user.token) {
            userService.invalidateOtp(user)
            TODO() //redirect to restore page
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid otp or token")
        }
    }
}