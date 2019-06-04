package kz.lalafa.jpademo.hib.tutorial.persistence

import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.SelectBeforeUpdate
import org.hibernate.annotations.Where
import javax.persistence.*


@Entity(name = "Book")
//@SelectBeforeUpdate(false)
data class Book(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = null,

        var title: String? = null,

        @NaturalId
        val isbn: String? = null,

        @ManyToOne
        val author: Person? = null
) {
    override fun toString(): String {
        return "Book(id=$id, title=$title, isbn=$isbn, author=${author?.name})"
    }
}