package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.AuthenticationResponse
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class PhoneAuthService(
        private val userService: UserService,
        private val smsService: SmsService,
        private val otpTokenService: OtpTokenService
) : PhoneAuthServiceInterface {

    override fun requestOtp(phone: String): OtpDto {

        userService.getUserByPhone(phone)?.let {
            if (userService.isRegistered(it))
                throw Exception("User already registered")
            if (userService.isOtpActual(it))
                throw Exception("Waiting for SMS")
        }

        val otp = otpTokenService.generateOtp()
        smsService.sendOtp(phone, otp.smsCode)
        userService.assignOtp(phone, otp.smsCode, otp.token)
        return otp.copy(smsCode = "")
    }

    override fun validateOtp(otp: String, token: String): AuthenticationResponse {

        val user = userService.getUserByToken(token)
                ?: throw IllegalArgumentException("Can't login. No such token")

        if (userService.isRegistered(user))
            throw Exception("user already registered. Restore your password.")

        if (userService.isOtpActual(user) && otp == user.smsCode && token == user.token) {
            userService.invalidateOtp(user)
            return AuthenticationResponse("", "", emptyList())
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid smsCode or token")
        }
    }
}

interface PhoneAuthServiceInterface {

    fun requestOtp(phone: String): OtpDto

    fun validateOtp(otp: String, token: String): AuthenticationResponse
}