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
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import utils.LogInManager

object Ktor {
    const val BASE_URL = "https://offer.sa/portal-api"
    // const val BASE_URL = "http://141.147.137.116"

    val client: HttpClient = HttpClient {
        // JSON configuration
        install(ContentNegotiation) {
            json(
                Json {
                    encodeDefaults = true
                    prettyPrint = true
                    ignoreUnknownKeys = true // Ignore extra keys in JSON
                    isLenient = true // Allow lenient JSON parsing
                }
            )
        }

        configure()

        // Custom Logging configuration
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL // Log everything
        }

        // Default request setup
        defaultRequest {
            url {
                takeFrom(BASE_URL) // Ensures relative paths are appended to BASE_URL
            }
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Accept, ContentType.Application.Json)
            LogInManager.getLoggedInUser()?.token?.let { token ->
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}
