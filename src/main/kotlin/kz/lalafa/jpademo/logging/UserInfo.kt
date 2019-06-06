package kz.lalafa.jpademo.logging

import java.time.Instant
import java.util.Optional
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
        var activeOtp: Boolean,
        var verifyCounter: Byte,
        var createdDateTime: Instant
) {
    fun ifRegistered(consumer: (UserInfo) -> Unit) {
        if (login != null)
            consumer(this)
    }
}

fun Optional<UserInfo>.ifRegistered(consumer: (UserInfo) -> Unit) {
    this.filter { it.login != null }
            .ifPresent {
                consumer(this.get())
            }
}

fun Optional<UserInfo>.ifOtpNotExpired(attemptsAllowed: Byte, durationAllowed: Long, consumer: (UserInfo) -> Unit) {
    this.filter { it.verifyCounter < attemptsAllowed && it.createdDateTime.plusSeconds(durationAllowed) < Instant.now() }
            .ifPresent {
                consumer(this.get())
            }
}