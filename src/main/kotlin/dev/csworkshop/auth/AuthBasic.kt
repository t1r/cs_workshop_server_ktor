package dev.csworkshop.auth

import dev.csworkshop.auth.AuthService.hashedUserTable
import io.ktor.server.auth.*

internal fun AuthenticationConfig.setupBasicAuth() {
    basic(AUTH_CONFIGURATION_NAME) {
        realm = "Access to the '/' path"
        validate { credentials ->
            hashedUserTable.authenticate(credentials)
        }
    }
}
