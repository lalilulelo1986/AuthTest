package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.UserInfo
import kz.lalafa.jpademo.logging.repository.UserRepository2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Service
import java.time.Instant
import javax.transaction.Transactional

@Service
@Transactional
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository2

    fun getUserByPhone(phone: String) = userRepository.findByPhone(phone)

    fun deleteUser(userInfo: UserInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUserByLogin(login: String) = userRepository.findByLogin(login)

    fun isRegistered(userInfo: UserInfo) = userInfo.login != null

    fun isOtpActual(userInfo: UserInfo): Boolean {
        return userInfo.activeOtp
                && userInfo.verifyCounter < 5
                && userInfo.createdDateTime.plusSeconds(300) < Instant.now()
    }

    fun assignOtp(phone: String, otp: String, token: String) {
        val user = userRepository.findByPhone(phone)
                ?.also {
                    it.otp = otp
                    it.token = token
                    it.createdDateTime = Instant.now()
                    it.verifyCounter = 0
                    it.activeOtp = true
                }
                ?: UserInfo(phone = phone, token = token, otp = otp,
                        activeOtp = true, verifyCounter = 0, createdDateTime = Instant.now())
        userRepository.save(user)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun register(userInfo: UserInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUserByToken(token: String): UserInfo? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Modifying
    fun invalidateOtp(userInfo: UserInfo) {
        userRepository.findByPhone(userInfo.phone)?.let {
            it.activeOtp = false
        } ?: throw Exception("No such user")
    }

    fun increaseOtpAttempt(userInfo: UserInfo) {
        //userInfo.verifyCounter ++
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}