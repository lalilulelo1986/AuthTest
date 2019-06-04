package kz.lalafa.jpademo.hib.tutorial.persistence

import org.springframework.data.repository.CrudRepository

interface BookRepo : CrudRepository<Book, Long>