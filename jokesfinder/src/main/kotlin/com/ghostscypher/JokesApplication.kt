package com.ghostscypher

import com.ghostscypher.data.Jokes
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@SpringBootApplication
@ComponentScan("com.ghostscypher.scheduledtasks")
@EnableRedisRepositories
class JokesFinderApplication(private val factory: RedisConnectionFactory) {

	@Bean
	fun redisOperations(): RedisOperations<String, Jokes> {
		val serializer: Jackson2JsonRedisSerializer<Jokes> = Jackson2JsonRedisSerializer(Jokes::class.java)
		val template: RedisTemplate<String, Jokes> = RedisTemplate()
		template.setConnectionFactory(factory)
		template.setDefaultSerializer(serializer)
		template.keySerializer = StringRedisSerializer()

		return template
	}

}

fun main(args: Array<String>) {
	runApplication<JokesFinderApplication>(*args)
}
