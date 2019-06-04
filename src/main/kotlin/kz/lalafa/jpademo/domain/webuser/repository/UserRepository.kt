package kz.lalafa.jpademo.domain.webuser.repository

import kz.lalafa.jpademo.domain.webuser.persistent.WebsiteUser
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
interface UserRepository : PagingAndSortingRepository<WebsiteUser, Long> {
    fun findByName(@Param("name") name: String): List<WebsiteUser>
}
