package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeplayground.ui.Home
import com.example.composeplayground.ui.ListWithSections
import com.example.composeplayground.ui.Login
import com.example.composeplayground.ui.theme.ComposePlaygroundTheme


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
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

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
	ComposePlaygroundTheme {
		App()
	}
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun App() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = "puppyList") {
		composable("login") { Login(navController) }
		composable("home") { Home(navController) }
		composable("puppyList") { ListWithSections() }
	}
}
