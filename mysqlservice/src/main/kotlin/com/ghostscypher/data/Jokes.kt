package com.ghostscypher.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.Instant
import javax.persistence.*

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Jokes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0

    @JsonProperty("id")
    private var joke_id: Long = 0

    @JsonProperty("category")
    private var category: String = ""

    @JsonProperty("type")
    private var type: String = ""

    @JsonProperty("setup")
    private var setup: String = ""

    @JsonProperty("delivery")
    private var delivery: String? = null

    @JsonProperty("flags")
    @ElementCollection(targetClass=Boolean::class)
    @MapKeyColumn(name="flag")
    private var flags: Map<String, Boolean> = mapOf()

    @JsonProperty("lang")
    private var lang: String = ""

    @JsonProperty("safe")
    private var safe: Boolean = true

    @JsonProperty("error")
    private var error: Boolean = false

    @JsonProperty("joke")
    private var joke: String = ""

    private var timestamp: Instant = Instant.now()

    fun getID(): Long {
        return id
    }

    fun getJokeID(): Long {
        return joke_id
    }

    fun getJoke(): String {
        return if (type == "single") {
            joke
        } else {
            "$setup $delivery"
        }
    }

    // toString() is used to print the object
    override fun toString(): String {
        return "Jokes(id=$id, category='$category', type='$type', setup='$setup', delivery=$delivery, flags=$flags, lang='$lang', safe=$safe, error=$error, joke='$joke', timestamp='$timestamp')"
    }
}