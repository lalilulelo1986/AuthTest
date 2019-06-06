package kz.lalafa.jpademo.logging.service

import org.springframework.stereotype.Service

@Service
class OtpTokenService {

    fun generateOtp(): OtpToken {
        TODO()
    }
}

data class OtpToken(
        val otp: String,
        val token: String
)