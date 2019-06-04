package kz.lalafa.jpademo.logging.repository

import kz.lalafa.jpademo.logging.UserInfo
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserRepository2 {

    fun findByPhone(phone: String): Optional<UserInfo> {
        //return Optional.of(UserInfo("mail@sdf.ru", "343435252", "login", "pass", true))
        return Optional.empty()
    }

    fun delete(id: UUID) {
        TODO()
    }

    fun findByLogin(login: String): Optional<UserInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}