package hu.aharcsa.organizer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OrganizerApplication

fun main(args: Array<String>) {
	runApplication<OrganizerApplication>(*args)
}
