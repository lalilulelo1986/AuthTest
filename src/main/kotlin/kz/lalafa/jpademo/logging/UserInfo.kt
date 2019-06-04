package kz.lalafa.jpademo.logging

import java.time.Instant
import java.util.*

data class UserInfo(
        val id: UUID,
        val email: String?,
        val phone: String,
        val login: String?,
        val password: String?,
        val activeOtp: Boolean,
        val token: String,
        val otp: String,
        val verifyCounter: Byte,
        val createdDateTime: Instant
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