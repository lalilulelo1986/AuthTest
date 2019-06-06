package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.AuthenticationResponse
import org.springframework.stereotype.Service

@Service
class RestoreUserService(
        private val userService: UserService,
        private val smsService: SmsService,
        private val otpTokenService: OtpTokenService
) : RestoreUserServiceInterface {

    override fun requestOtp(phone: String): OtpDto {

        val user = userService.getUserByPhone(phone)
                ?: throw Exception("User not found")

        if (!userService.isRegistered(user))
            throw Exception("User with this phone not registered")
        if (userService.isOtpActual(user))
            throw Exception("Waiting for SMS")

        val otp = otpTokenService.generateOtp()
        smsService.sendOtp(phone, otp.smsCode)
        userService.assignOtp(phone, otp.smsCode, otp.token)
        return otp.copy(smsCode = "")
    }

    override fun validateOtp(otp: String, token: String): AuthenticationResponse {

        val user = userService.getUserByToken(token)
                ?: throw IllegalArgumentException("Can't restore. No such token")

        if (!userService.isRegistered(user))
            throw Exception("User not registered")

        if (userService.isOtpActual(user) && otp == user.smsCode && token == user.token) {
            userService.invalidateOtp(user)
            return AuthenticationResponse("", "", emptyList())
        } else {
            userService.increaseOtpAttempt(user)
            throw Exception("Not valid smsCode or token")
        }
    }
}

interface RestoreUserServiceInterface {

    fun requestOtp(phone: String): OtpDto

    fun validateOtp(otp: String, token: String): AuthenticationResponse
}