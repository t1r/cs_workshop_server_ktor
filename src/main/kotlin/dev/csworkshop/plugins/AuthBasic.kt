package dev.csworkshop.plugins

import dev.csworkshop.auth.AuthService
import dev.csworkshop.plugins.Constants.PREFIX_API
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRouting() {
    routing {
        post("$PREFIX_API/auth") {
            try {
                val parameters = call.receiveParameters()
                val name = parameters["name"]
                val password = parameters["password"]
                if (name.isNullOrBlank() || password.isNullOrBlank()) throw RuntimeException()

                AuthService.addCredential(name, password)
                call.respondText("Auth successful", status = HttpStatusCode.OK)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

