package com.ghostscypher.learning.interfaces

import com.ghostscypher.learning.Coffee
import org.springframework.data.repository.CrudRepository

interface CoffeeRepository : CrudRepository<Coffee, String> {
    // Add custom methods
    fun findByName(name: String): List<Coffee>
}