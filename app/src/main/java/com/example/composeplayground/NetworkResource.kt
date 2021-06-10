package com.example.composeplayground

sealed class NetworkResource<out T> {
	class Success<out T>(val data: T) : NetworkResource<T>() {
		override fun toString(): String {
			return "Success($data)"
		}
	}

	class Error(val exception: Throwable) : NetworkResource<Nothing>() {
		override fun toString(): String {
			return javaClass.simpleName + "/" + exception.stackTraceToString()
		}
	}
}
