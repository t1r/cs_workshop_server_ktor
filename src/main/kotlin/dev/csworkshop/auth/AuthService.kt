package dev.csworkshop.auth

import io.ktor.server.auth.*
import io.ktor.util.*

object AuthService {
    internal val digestFunction = getDigestFunction("SHA-256") { "ktor${it.length}" }
    private val credentialsMap = mutableMapOf(
        "root" to digestFunction("root"),
    )

    internal val hashedUserTable = UserHashedTableAuth(
        table = credentialsMap,
        digester = digestFunction
    )

    fun addCredential(name: String, password: String) {
        credentialsMap[name] = digestFunction(password)
    }
}