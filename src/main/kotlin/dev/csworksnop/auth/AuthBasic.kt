package dev.csworksnop.auth

import io.ktor.server.auth.*
import io.ktor.util.*

internal val digestFunction = getDigestFunction("SHA-256") { "ktor${it.length}" }
internal val hashedUserTable = UserHashedTableAuth(
    table = mapOf(
        "jetbrains" to digestFunction("foobar"),
        "admin" to digestFunction("password"),
        "root" to digestFunction("root"),
    ),
    digester = digestFunction
)

internal fun AuthenticationConfig.setupBasicAuth() {
    basic(AUTH_CONFIGURATION_NAME) {
        realm = "Access to the '/' path"
        validate { credentials ->
            hashedUserTable.authenticate(credentials)
        }
    }
}
