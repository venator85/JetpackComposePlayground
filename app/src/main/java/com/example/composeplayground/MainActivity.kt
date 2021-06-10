package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ComposePlaygroundTheme {
				Surface(color = MaterialTheme.colors.background) {
					App()
				}
			}
		}
	}
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
	ComposePlaygroundTheme {
		App()
	}
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun App() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = "login") {
		composable("login") { Login(navController) }
		composable("home") { Home(navController) }
	}
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun Login(navController: NavHostController) {
	val keyboardController = LocalSoftwareKeyboardController.current

	// https://stackoverflow.com/questions/64951605/var-value-by-remember-mutablestateofdefault-produce-error-why
	var email by remember { mutableStateOf("") }
	var password by remember { mutableStateOf("") }

	var loading by remember { mutableStateOf(false) }

	val scope = rememberCoroutineScope()
	var loginResponse by remember { mutableStateOf<NetworkResource<Token>?>(null) }

	Column(Modifier.padding(16.dp)) {
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
				scope.launch {
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

		AnimatedVisibility(
			visible = loading,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
				.padding(16.dp)
		) {
			CircularProgressIndicator()
		}
	}

	when (val loginResponseValue = loginResponse) {
		is NetworkResource.Success -> {
			navController.navigate("home") {
				popUpTo("login") { inclusive = true }
			}
		}
		is NetworkResource.Error -> {
			AlertDialog(
				title = { Text(text = "Error") },
				text = { Text(text = loginResponseValue.exception.toString()) },
				confirmButton = {
					Button(
						onClick = {
							loginResponse = null // rimettendo la loginResponse a null triggeriamo una recomposition, ma non entriamo piu in questo ramo del when, quindi la dialog viene nascosta
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

@Composable
fun Home(navController: NavHostController) {
	Column(Modifier.padding(16.dp)) {
		Text("Welcome")
	}
}
