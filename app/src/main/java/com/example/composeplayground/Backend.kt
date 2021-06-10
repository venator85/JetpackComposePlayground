package com.example.composeplayground

import com.example.composeplayground.NetworkResource.Error
import com.example.composeplayground.NetworkResource.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

object Backend {

	suspend fun login(username: String, password: String): NetworkResource<Token> {
		return withContext(Dispatchers.IO) {
			delay(1000)
			when (password) {
				"z" -> Error(ServerException("incorrect credentials"))
				"a" -> Success(Token(UUID.randomUUID().toString()))
				else -> Error(IOException())
			}
		}
	}

}

data class Token(val token: String)

class ServerException(message: String) : Exception(message)
