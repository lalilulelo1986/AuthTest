package kz.lalafa.jpademo.hib.tutorial.persistence

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity(name = "Person")
//@Where(clause = "name = 'masss'")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        val name: String? = null,

        @OneToMany(mappedBy = "author")
        @JsonIgnore
        val books: List<Book>? = emptyList()


) {
    override fun toString(): String {
        return "Person(id=$id, name=$name, books=$books)"
    }
}