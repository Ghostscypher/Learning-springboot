package com.ghostscypher.learning.interfaces

import com.ghostscypher.learning.Coffee
import org.springframework.data.repository.CrudRepository

interface CoffeeRepository : CrudRepository<Coffee, String> {}