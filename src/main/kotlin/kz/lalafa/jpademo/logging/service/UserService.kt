package kz.lalafa.jpademo.logging.service

import kz.lalafa.jpademo.logging.UserInfo
import kz.lalafa.jpademo.logging.repository.UserRepository2
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository2

    @Autowired
    private lateinit var smsService: SmsService

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

    fun updateOtp(phone: String, otp: String, token: String) {
        val user = userRepository.findByPhone(phone).get()
        user.createdDateTime
        user.activeOtp
        user.otp
        user.token
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun register(userInfo: UserInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUserByToken(token: String): Optional<UserInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun invalidateOtp(userInfo: UserInfo) {
        //userInfo.activeOtp = false
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun increaseOtpAttempt(userInfo: UserInfo) {
        //userInfo.verifyCounter ++
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}