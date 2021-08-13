package com.example.composeplayground.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeplayground.Backend
import com.example.composeplayground.NetworkResource
import com.example.composeplayground.Token
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Login(navController: NavHostController) {
	val keyboardController = LocalSoftwareKeyboardController.current
	val networkRequestScope = rememberCoroutineScope()

	var email by remember { mutableStateOf("") }
	var password by remember { mutableStateOf("") }

	var loading by remember { mutableStateOf(false) }

	var loginResponse by remember { mutableStateOf<NetworkResource<Token>?>(null) }

	Column(Modifier.padding(16.dp)) {
		Text(
			modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
			text = "The password is 'a', email is ignored \uD83D\uDE09"
		)
		TextField(
			modifier = Modifier.fillMaxWidth(),
			value = email,
			onValueChange = { email = it },
			label = { Text("Email") },
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
			enabled = !loading
		)
		TextField(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 16.dp),
			value = password,
			onValueChange = { password = it },
			label = { Text("Password") },
			visualTransformation = PasswordVisualTransformation(),
			enabled = !loading
		)
		Button(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 16.dp),
			onClick = {
				networkRequestScope.launch {
					keyboardController?.hide()
					loading = true
					loginResponse = Backend.login(email, password)
					loading = false
				}
			},
			enabled = !loading
		) {
			Text("Login")
		}

		if (loading) {
			Box(
				modifier = Modifier
					.align(Alignment.CenterHorizontally)
					.padding(16.dp)
			) {
				CircularProgressIndicator()
			}
		}
	}

	when (val loginResponseValue = loginResponse) {
		is NetworkResource.Success -> {
			navController.navigate("home") {
				popUpTo("login") { inclusive = true }
			}
			loginResponse = null
		}
		is NetworkResource.Error -> {
			AlertDialog(
				title = { Text(text = "Error") },
				text = { Text(text = loginResponseValue.exception.toString()) },
				confirmButton = {
					Button(
						onClick = {
							// by setting loginResponse to null we trigger a recomposition, but we don't enter
							// in this branch of the when and the dialog is no longer shown!
							loginResponse = null
						}) {
						Text("OK")
					}
				},
				onDismissRequest = {
					loginResponse = null
				},
			)
		}
	}
}
