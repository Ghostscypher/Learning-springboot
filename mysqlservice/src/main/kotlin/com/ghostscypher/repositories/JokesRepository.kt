package com.ghostscypher.repositories

import com.ghostscypher.data.Jokes
import org.springframework.data.repository.CrudRepository

interface JokesRepository: CrudRepository<Jokes, String>
