package com.ghostscypher.scheduledtasks

import com.ghostscypher.data.Jokes
import com.ghostscypher.repositories.JokesRepository
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
@EnableScheduling
class JokesFinderPoller(
    private val redisOperations: RedisOperations<String, Jokes>,
    private val webClient: WebClient = WebClient.create("https://v2.jokeapi.dev/joke/Any"),
    private val jokesRepository: JokesRepository
    ){

//    @Scheduled(fixedRate = 5000)
    fun poll() {
        webClient.get().retrieve().bodyToFlux(Jokes::class.java).subscribe {
            redisOperations.opsForValue().set(it.getID().toString(), it)
        }

        redisOperations.opsForValue().operations.keys("*")?.forEach {
            println("....................................................")
            println(redisOperations.opsForValue().get(it)?.getJoke())
            println("....................................................")
        }
    }

    @Scheduled(fixedRate = 5000)
    fun poll2() {
        webClient.get().retrieve().bodyToFlux(Jokes::class.java).subscribe {
            jokesRepository.save(it)

            println("....................................................")
            println(it.getJoke())
            println("....................................................")
        }

        // Uncomment to print all jokes currently in the repo
//        jokesRepository.findAll().forEach {
//            println("....................................................")
//            println(it.getJoke())
//            println("....................................................")
//        }
    }
}