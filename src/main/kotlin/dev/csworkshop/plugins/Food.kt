package dev.csworkshop.plugins

import dev.csworkshop.auth.AUTH_CONFIGURATION_NAME
import dev.csworkshop.plugins.Constants.PREFIX_API
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.foodRouting() {
    val foodStorage = mutableListOf<FoodModel>().apply {
        addAll(
            arrayOf(
                FoodModel(1, "Cucumber", 100F),
                FoodModel(2, "Potato", 100F),
                FoodModel(3, "Onion", 100F),
                FoodModel(4, "Bread", 100F),
                FoodModel(5, "Sesame", 100F),
            )
        )
    }

    routing {
        authenticate(AUTH_CONFIGURATION_NAME) {
            get("$PREFIX_API/foodList") {
                call.respond(foodStorage)
            }
            get("$PREFIX_API/food/{id}") {
                val id = call.parameters["id"]
                val model = foodStorage.firstOrNull { m -> m.id == id?.toLong() }

                if (model != null) call.respond(model)
                else call.respond(HttpStatusCode.NotFound)
            }
            post("$PREFIX_API/addFood") {
                try {
                    val lastId = foodStorage.lastOrNull()?.id
                    val id = when {
                        lastId != null -> lastId + 1
                        else -> 0
                    }
                    val parameters = call.receiveParameters()
                    val name = parameters["name"]!!
                    val weight = parameters["weight"]?.toFloatOrNull()!!
                    foodStorage.add(
                        FoodModel(
                            id = id,
                            name = name,
                            weight = weight,
                        )
                    )
                    call.respondText("Add success", status = HttpStatusCode.Created)
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
}

@Serializable
data class FoodModel(
    val id: Long,
    val name: String,
    val weight: Float,
)
