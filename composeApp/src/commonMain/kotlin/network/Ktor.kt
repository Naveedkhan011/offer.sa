package network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import utils.LogInManager

object Ktor {
    private const val BASE_URL = "https://offer.sa"

    val client: HttpClient = HttpClient {
        // JSON configuration
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true // Ignore extra keys in JSON
                    isLenient = true // Allow lenient JSON parsing
                }
            )
        }

        configure()

        // Custom Logging configuration
        install(Logging) {
            /*logger = object : Logger {
                override fun log(message: String) {
                    println("Ktor Log: $message")
                }
            }*/
            logger = Logger.DEFAULT
            level = LogLevel.ALL // Log everything
        }

        // Default request setup
        defaultRequest {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Accept, ContentType.Application.Json)

            /*if (LogInManager.getLoggedInUser() != null) {
                header(
                    HttpHeaders.Authorization,
                    "Bearer " + LogInManager.getLoggedInUser()!!.token
                )
            }*/
            url(BASE_URL) // Set the base URL
        }
    }
}
