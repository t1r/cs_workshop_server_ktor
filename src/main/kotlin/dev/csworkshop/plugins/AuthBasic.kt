package dev.csworkshop.plugins

import dev.csworkshop.auth.hashedUserTable
import dev.csworkshop.plugins.Constants.PREFIX_API
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRouting() {
    routing {
        post("$PREFIX_API/auth") {
            try {
                val parameters = call.receiveParameters()
                val name = parameters["name"]!!
                val password = parameters["password"]!!

                val id = hashedUserTable.authenticate(
                    UserPasswordCredential(
                        name = name,
                        password = password,
                    )
                )
                call.respondText("Авторизация успешна", status = HttpStatusCode.OK)
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}

