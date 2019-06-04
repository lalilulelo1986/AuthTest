package kz.lalafa.jpademo.hib.tutorial.persistence

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManagerFactory
import org.hibernate.stat.Statistics
import org.hibernate.SessionFactory
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException
import javax.persistence.EntityManager


@RunWith(SpringRunner::class)
@SpringBootTest
class BookTest {

    @Autowired
    lateinit var bookService: BookService

    @Autowired
    lateinit var personRepo: PersonRepo

    @Autowired
    lateinit var entityManagerFactory: EntityManagerFactory

    @Autowired
    lateinit var entityManager: EntityManager

//    @Test
//    fun test() {
//        Assert.assertNotNull(bookRepo)
//
//        val person = Person(name = "max")
//        personRepo.save(person)
//
//        val book1 = Book(title = "white fang", author = person)
//        val book2 = Book(title = "martin eden", author = person)
//
//        bookRepo.saveAll(mutableListOf(book1, book2))
//    }

    @Test
    fun findByNaturalId() {
        val sessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)
        val openSession = sessionFactory.openSession()
        val transaction = openSession.beginTransaction()

        val book = openSession.find(Book::class.java, 9L)
        println("book: $book")
        transaction.commit()
        openSession.close()

        //val book1 = entityManager.find(Book::class.java, 9L)
        //println("bood1: $book1")
    }

    @Test
    @Transactional(readOnly = true)
    fun findByCriteria() {
        entityManager.createQuery("select p from Person p", Person::class.java)
                .resultList
                .forEach { println(it) }

    }

    @Test
    fun merge() {
        val sessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)
        val openSession = sessionFactory.openSession()
        val transaction = openSession.beginTransaction()

        val book = Book(id = 9L, title = "white fang 2")//openSession.find(Book::class.java, 9L)
        val merge = openSession.merge(book)
        println("book: $merge")
        transaction.commit()
        openSession.close()
    }

    @Test
    fun testUpdateWithDetuched() {
        bookService.testUpdateWithDetuched()
    }

    @Test
    fun testUpdateWithPersisted() {
        bookService.testUpdateWithPersisted()
    }


}


class Person2 {
    val name: String? = "max"
    val age: Int? = 31

    operator fun component1(): String {
        return ""
    }

    operator fun component2(): String {
        return ""
    }

    @Test
    fun test() {
        val intPlus: Int.(Int) -> Int = Int::plus

        println(2.intPlus(3)) // extension-like call

        val sum: Int.(Int) -> Int = { other -> plus(other) }
        println(3.sum(3))
        sum(1, 2)
        "asdf".plus("")

        val n = "sdf".let {

        }
        println(n)

        val myFun = fun(s: String) { s.toUpperCase() }
        myFun("sf")
        val l = run {
            "fds"
        }
        println(l)

        println(111.run {
            minus(1)
        })
    }

    fun te(person2: Person2) {
        val phone = person2.name ?: ""
        if (phone.isEmpty()) {
            fuckYou()
        }
    }

    fun te2(person2: Person2) {
        val phone = person2.name?.takeUnless { it.isBlank() }
                ?: throw RuntimeException()

        val d1 = d1("asdf")
        val (t) = d1
        val d2 = d2(t)
    }

    fun fuckYou(): Nothing {
        throw RuntimeException()
    }


}

data class d1(
        val name: String
)

data class d2(
        val name: String
)

