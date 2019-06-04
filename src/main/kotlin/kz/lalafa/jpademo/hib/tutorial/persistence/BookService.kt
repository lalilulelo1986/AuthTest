package kz.lalafa.jpademo.hib.tutorial.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService {

    @Autowired
    lateinit var bookRepo: BookRepo

    @Transactional
    public fun testUpdateWithDetuched() {
        val findById = bookRepo.findById(9L).get()
        println(findById)
        val updatedBook = Book(findById.id, "white fang 3", findById.isbn, author = null)
        val save = bookRepo.save(updatedBook)
        println(save)
    }

    @Transactional
    public fun testUpdateWithPersisted() {
        val findById = bookRepo.findById(9L).get()
        println(findById)
        findById.title = "white fang 2"
        val save = bookRepo.save(findById)
        println(save)
    }
}