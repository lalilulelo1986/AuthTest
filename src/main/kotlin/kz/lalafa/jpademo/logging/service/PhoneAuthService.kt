package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.AuthenticationResponse
import org.springframework.stereotype.Service

@Service
class PhoneAuthService(
        private val userService: UserService,
        private val smsService: SmsService,
        private val otpTokenService: OtpTokenService
) : PhoneAuthServiceInterface {

    override fun requestOtp(phone: String): String {

        userService.getUserByPhone(phone)?.let {
            if (userService.isRegistered(it))
                throw Exception("User already registered")
            if (userService.isOtpActual(it))
                throw Exception("Waiting for SMS")
        }

        val (otp, token) = otpTokenService.generateOtp()
        smsService.sendOtp(phone, otp)
        userService.assignOtp(phone, otp, token)
        return token
    }

    override fun validateOtp(otp: String, token: String): AuthenticationResponse {

        val user = userService.getUserByToken(token)
                ?: throw IllegalArgumentException("Can't login. No such token")

        if (userService.isRegistered(user))
            throw Exception("user already registered. Restore your password.")

        if (userService.isOtpActual(user) && otp == user.otp && token == user.token) {
            userService.invalidateOtp(user)
            return AuthenticationResponse("", "", emptyList())
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid otp or token")
        }
    }
}

interface PhoneAuthServiceInterface {

    fun requestOtp(phone: String): String

    fun validateOtp(otp: String, token: String): AuthenticationResponse
}