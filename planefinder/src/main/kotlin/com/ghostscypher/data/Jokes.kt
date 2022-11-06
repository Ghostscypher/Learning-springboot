package com.ghostscypher.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.time.Instant

@Data
@RedisHash("Jokes")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class Jokes {
    @Id
    @JsonProperty("id")
    private var id: Long = 0

    @JsonProperty("category")
    private var category: String = ""

    @JsonProperty("type")
    private var type: String = ""

    @JsonProperty("setup")
    private var setup: String = ""

    @JsonProperty("delivery")
    private var delivery: String? = null

    @JsonProperty("flags")
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