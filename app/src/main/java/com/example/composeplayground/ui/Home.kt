package com.example.composeplayground.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
	val puppies = remember { mutableStateListOf<Puppy>() }
	for (i in 1..100) {
		puppies.add(Puppy(i))
	}

	val toggleAdoption: (Int) -> Unit = { i ->
		// assignment to puppies[i] calls the set() method of SnapshotStateList,
		// that re-evaluates the equals of the list,
		// that re-evaluates the equals of each item in the list,
		// and that triggers a recomposition, showing the updated PuppyItem
		puppies[i] = puppies[i].apply {
			adopted = !adopted
		}
	}

	LazyColumn {
		items(puppies.size) { idx ->
			PuppyItem(puppies[idx], idx, idx == puppies.lastIndex, toggleAdoption)
		}
	}
}

data class Puppy(
	val id: Int,
	val name: String = "Dog $id",
	var adopted: Boolean = id.rem(4) == 0,
)

@Composable
fun PuppyItem(puppy: Puppy, position: Int, last: Boolean, onClick: (Int) -> Unit) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = if (last) 16.dp else 0.dp)
			.clickable {
				onClick(position)
			},
		elevation = 8.dp
	) {
		val color = if (puppy.adopted) Color(0x8000ff00) else Color.White
		Column(
			Modifier
				.background(color)
				.padding(8.dp)
		) {
			Text(
				text = puppy.name,
				fontWeight = FontWeight.Bold,
				modifier = Modifier
					.fillMaxWidth()
			)
			Text(
				text = "adopted: ${puppy.adopted}",
				modifier = Modifier
					.fillMaxWidth()
			)
		}
	}
}
