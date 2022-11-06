package com.ghostscypher.learning

import com.ghostscypher.learning.interfaces.CoffeeRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.PostConstruct
import javax.persistence.*

@SpringBootApplication
@ConfigurationPropertiesScan
class LearningApplication {
	@Bean
	@ConfigurationProperties(prefix = "droid")
	fun createDroid(): Droid {
		return Droid()
	}
}

fun main(args: Array<String>) {
	runApplication<LearningApplication>(*args)
}

@Entity
class Coffee {
	@Id
	@Column(name = "id", nullable = false)
	private var id: String

	private var name: String

	constructor(): this("", "")

	constructor(id: String, name: String) {
		this.id = id
		this.name = name
	}

	constructor(name: String): this(UUID.randomUUID().toString(), name)

	fun getId(): String {
		return id
	}

	fun setId(id: String) {
		this.id = id
	}

	fun getName(): String {
		return name
	}

	fun setName(name: String) {
		this.name = name
	}

	override fun toString(): String = "Coffee(id='$id', name='$name')"
}

@RestController
@RequestMapping("/coffees")
class CoffeeController  {
	private val coffees: MutableList<Coffee> = mutableListOf()

	init {
		coffees.add(Coffee("Cappuccino"))
		coffees.add(Coffee("Espresso"))
		coffees.add(Coffee("Latte"))
	}

	@GetMapping
	fun getCoffees(): List<Coffee> {
		return coffees
	}

	@GetMapping("/{id}")
	fun getCoffee(@PathVariable id: String): Coffee? {
		return coffees.find { it.getId() == id }
	}

	@PostMapping
	fun addCoffee(@RequestBody coffee: Coffee): Coffee {
		coffees.add(coffee)
		return coffee
	}

	@PutMapping("/{id}")
	fun updateCoffee(@PathVariable id: String, @RequestBody coffee: Coffee): ResponseEntity<Coffee> {
		val index = coffees.indexOfFirst { it.getId() == id }

		if (index == -1) {
			return ResponseEntity<Coffee>(addCoffee(coffee), HttpStatus.CREATED)
		}

		coffees[index] = coffee
		return ResponseEntity.ok(coffee)
	}

	@DeleteMapping("/{id}")
	fun deleteCoffee(@PathVariable id: String): List<Coffee> {
		coffees.removeIf { it.getId() == id }
		return coffees
	}
}

@Component
class DataInitializer(private val coffeeRepository: CoffeeRepository) {

	@PostConstruct
	fun loadData() {
		this.coffeeRepository.saveAll(
			listOf(
				Coffee("Cappuccino"),
				Coffee("Espresso"),
				Coffee("Latte"),
				Coffee("Mocha"),
				Coffee("Americano"),
				Coffee("Macchiato"),
				Coffee("Cortado"),
				Coffee("Flat White"),
				Coffee("Affogato"),
				Coffee("Cafe Bombon"),
				Coffee("Cafe Cubano"),
				Coffee("Cafe Con Leche"),
				Coffee("Cafe Crema"),
				Coffee("Cafe Latte"),
				Coffee("Cafe Zorro"),
				Coffee("Cafe Miel"),
				Coffee("Cafe Olla"),
				Coffee("Cafe Bombon")
			)
		)
	}
}

@RestController
@RequestMapping("/coffees2")
class CoffeeController2(private val coffeeRepository: CoffeeRepository) {

	@GetMapping
	fun getCoffees(): MutableIterable<Coffee> {
		return this.coffeeRepository.findAll()
	}

	@GetMapping("/{id}")
	fun getCoffee(@PathVariable id: String): Coffee? {
		return this.coffeeRepository.findById(id).orElse(null)
	}

	@PostMapping
	fun addCoffee(@RequestBody coffee: Coffee): Coffee {
		return this.coffeeRepository.save(coffee)
	}

	@PutMapping("/{id}")
	fun updateCoffee(@PathVariable id: String, @RequestBody coffee: Coffee): ResponseEntity<Coffee> {
		return this.coffeeRepository.existsById(id).let {
			if (it) {
				this.coffeeRepository.save(coffee)
				ResponseEntity.ok(coffee)
			} else {
				ResponseEntity<Coffee>(addCoffee(coffee), HttpStatus.CREATED)
			}
		}
	}

	@DeleteMapping("/{id}")
	fun deleteCoffee(@PathVariable id: String): List<Coffee> {
		this.coffeeRepository.deleteById(id)
		return this.coffeeRepository.findAll().toList()
	}
}

@RestController
@RequestMapping("/greetings")
class GreetingsController {

	@Value("\${greeting.name: World}")
	private lateinit var name: String

	@Value("\${greeting-coffee: \${greeting.name} might love coffee}")
	private lateinit var coffee: String

	@GetMapping()
	fun greetings(): String {
		return "Hello, ${this.name}!"
	}

	@GetMapping("/coffee")
	fun coffee(): String {
		return "${this.coffee}!"
	}
}

@ConfigurationProperties(prefix = "tea")
class Tea {
	private var id: String
	private var name: String

	constructor(): this("", "")

	constructor(id: String, name: String) {
		this.id = id
		this.name = name
	}

	constructor(name: String): this(UUID.randomUUID().toString(), name)

	fun getId(): String {
		return id
	}

	fun setId(id: String) {
		this.id = id
	}

	fun getName(): String {
		return name
	}

	fun setName(name: String) {
		this.name = name
	}

	override fun toString(): String {
		return "Tea(id='$id', name='$name')"
	}
}

@RestController
@RequestMapping("/greetings2")
class Greetings2Controller(private final val tea: Tea) {

	@GetMapping()
	fun greetings(): String {
		return "Hello, ${this.tea.getName()}!"
	}

	@GetMapping("/tea")
	fun coffee(): String {
		return "${this.tea}!"
	}
}

class Droid {
	private var id: String
	private var description: String

	constructor(): this("", "")

	constructor(id: String, description: String) {
		this.id = id
		this.description = description
	}

	constructor(description: String): this(UUID.randomUUID().toString(), description)

	fun getId(): String {
		return id
	}

	fun setId(id: String) {
		this.id = id
	}

	fun getDescription(): String {
		return description
	}

	fun setDescription(description: String) {
		this.description = description
	}
}

@RestController
@RequestMapping("/droid")
class DroidController(private final val droid: Droid) {
	@GetMapping()
	fun coffee(): Droid {
		return this.droid;
	}
}

