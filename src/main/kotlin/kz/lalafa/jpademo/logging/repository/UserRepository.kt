package kz.lalafa.jpademo.logging.repository

import kz.lalafa.jpademo.logging.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository2 : JpaRepository<UserInfo, UUID> {

    fun findByPhone(phone: String): UserInfo? {
        //return Optional.of(UserInfo("mail@sdf.ru", "343435252", "login", "pass", true))
        return null
    }

    fun findByLogin(login: String): Optional<UserInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}