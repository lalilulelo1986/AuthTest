package kz.lalafa.jpademo.logging

import java.time.Instant
import java.util.UUID
import javax.persistence.Entity

@Entity
data class UserInfo(
        val id: UUID? = UUID.randomUUID(),
        val email: String? = null,
        val phone: String,
        val login: String? = null,
        var password: String? = null,
        var token: String,
        var otp: String,
        var isActiveOtp: Boolean,
        var verifyCounter: Byte,
        var createdDateTime: Instant
)