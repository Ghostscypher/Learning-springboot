package com.ghostscypher.learning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.springframework.web.bind.annotation.*

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import java.util.UUID
import javax.persistence.*

@SpringBootApplication
class LearningApplication

fun main(args: Array<String>) {
	runApplication<LearningApplication>(*args)
}

@Entity
class Coffee {

	private var id: String
	private var name: String

	constructor(){
		this.id = UUID.randomUUID().toString()
		this.name = ""
	}

	constructor(id: String, name: String) {
		this.id = id
		this.name = name
	}

	constructor(name: String) {
		this.id = UUID.randomUUID().toString()
		this.name = name
	}

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
		return "Coffee(id='$id', name='$name')"
	}
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