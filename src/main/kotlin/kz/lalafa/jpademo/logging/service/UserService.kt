package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.UserInfo
import kz.lalafa.jpademo.logging.repository.UserRepository2
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import java.time.Instant
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
        private val userRepository: UserRepository2
) {

    fun getUserByPhone(phone: String) = userRepository.findByPhone(phone)

    fun deleteUser(userInfo: UserInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUserByLogin(login: String) = userRepository.findByLogin(login)

    fun isRegistered(userInfo: UserInfo) = userInfo.login != null

    fun isOtpActual(userInfo: UserInfo): Boolean {
        return userInfo.isActiveToken
                && userInfo.verifyCounter < 5
                && userInfo.createdDateTime.plusSeconds(300) < Instant.now()
    }

    fun assignOtp(phone: String, smsCode: String, token: String) {
        val user = userRepository.findByPhone(phone)
                ?.also {
                    it.smsCode = smsCode
                    it.token = token
                    it.createdDateTime = Instant.now()
                    it.verifyCounter = 0
                    it.isActiveToken = true
                }
                ?: UserInfo(phone = phone, token = token, smsCode = smsCode,
                        isActiveToken = true, verifyCounter = 0, createdDateTime = Instant.now())

        userRepository.save(user)
    }

    fun register(userInfo: UserInfo): UserInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUserByToken(token: String): UserInfo? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Modifying
    fun invalidateOtp(userInfo: UserInfo) {
        userRepository.findByPhone(userInfo.phone)?.apply {
            isActiveToken = false
        } ?: throw Exception("No such user")
    }

    @Modifying
    fun increaseOtpAttempt(userInfo: UserInfo) {
        userRepository.findByPhone(userInfo.phone)?.apply {
            verifyCounter++
        } ?: throw Exception("No such user")
    }
}