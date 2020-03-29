package com.example.journal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/journals")
class JournalController(@Autowired private val journalRepository : JournalRepository) {
    //gets all journals
    @GetMapping("")
    fun getAllJournals() : List<Journal> = journalRepository.findAll()

    //creates a journal
    @PostMapping("")
    fun createJournal(@Valid @RequestBody journal : Journal) : Journal = journalRepository.save(journal)

    //gets a single journal
    @GetMapping("/{journalId}")
    fun getJournalById(@PathVariable journalId : Long) : ResponseEntity<Journal> =
            journalRepository.findById(journalId).map {
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    //updates a journal
    @PutMapping("/{journalId}")
    fun updateJournal(@PathVariable journalId : Long,@Valid @RequestBody updatedJournal: Journal)
            : ResponseEntity<Journal> =
            journalRepository.findById(journalId).map{
                val newJournal = it.copy(title = updatedJournal.title, content =  updatedJournal.content)
                ResponseEntity.ok().body(journalRepository.save(newJournal))
            }.orElse(ResponseEntity.notFound().build())

    // deletes a journal
    @DeleteMapping("/{journalId}")
    fun deleteJournal(@PathVariable journalId : Long) : ResponseEntity<Void> =
            journalRepository.findById(journalId).map{
                journalRepository.delete(it)
                ResponseEntity<Void>(HttpStatus.OK)
            }.orElse(ResponseEntity.notFound().build())
}
