package dev.csworkshop.plugins

import dev.csworkshop.auth.AUTH_CONFIGURATION_NAME
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {
    routing {
        authenticate(AUTH_CONFIGURATION_NAME) {
            get("/") {
                call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}!")
            }
        }
        get("/test") {
            call.respondText("Hello!")
        }
    }
}
