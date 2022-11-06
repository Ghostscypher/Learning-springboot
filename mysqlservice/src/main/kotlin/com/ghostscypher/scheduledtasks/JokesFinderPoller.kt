package com.ghostscypher.scheduledtasks

import com.ghostscypher.data.Jokes
import com.ghostscypher.repositories.JokesRepository
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
@EnableScheduling
class JokesFinderPoller(
    private val webClient: WebClient = WebClient.create("https://v2.jokeapi.dev/joke/Any"),
    private val jokesRepository: JokesRepository
    ){

    @Scheduled(fixedRate = 5000)
    fun poll() {
        webClient.get().retrieve().bodyToFlux(Jokes::class.java).subscribe {
            jokesRepository.save(it)

            println("....................................................")
            println(it.getJoke())
            println("....................................................")
        }
    }
}