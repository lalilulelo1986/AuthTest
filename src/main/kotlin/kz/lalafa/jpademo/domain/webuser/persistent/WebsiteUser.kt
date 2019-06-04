package kz.lalafa.jpademo.domain.webuser.persistent

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class WebsiteUser(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        var name: String,
        var email: String
)