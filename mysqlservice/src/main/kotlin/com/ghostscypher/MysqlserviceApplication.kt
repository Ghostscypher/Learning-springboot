package com.ghostscypher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan("com.ghostscypher.scheduledtasks")
@EnableJpaRepositories
@EntityScan("com.ghostscypher.data")
class MysqlserviceApplication

fun main(args: Array<String>) {
	runApplication<MysqlserviceApplication>(*args)
}
