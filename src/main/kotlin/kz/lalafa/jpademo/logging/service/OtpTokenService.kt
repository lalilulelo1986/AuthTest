package kz.lalafa.jpademo.logging.service

import org.springframework.stereotype.Service
import java.time.Duration
import java.util.UUID

@Service
class OtpTokenService {

    fun generateOtp(): OtpDto {
        return OtpDto("1231", UUID.randomUUID().toString(), Duration.ofSeconds(300))
    }
}

data class OtpDto(
        val smsCode: String,
        val token: String,
        val duration: Duration
)