package dev.csworksnop

import dev.csworksnop.auth.setupBasicAuth
import dev.csworksnop.plugins.authRouting
import dev.csworksnop.plugins.configureRouting
import dev.csworksnop.plugins.configureSecurity
import dev.csworksnop.plugins.foodRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
    )
        .start(wait = true)
}

fun Application.module() {
    install(Authentication) {
        setupBasicAuth()
    }
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
    authRouting()
    foodRouting()

    configureSecurity()
    configureRouting()
}