package kz.lalafa.jpademo.hib.tutorial.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("persons")
class PersonController {

    @Autowired
    lateinit var personRepo: PersonRepo

    @GetMapping
    fun getAll() : List<Person> {
        return personRepo.findAll().toList()
    }
}