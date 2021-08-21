package com.example.composeplayground.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Preview
@Composable
fun ListWithSections() {
	val puppiesState = remember {
		mutableStateListOf<PuppyModel>().apply {
			for (i in 1..20) {
				add(PuppyModel(i))
			}
		}
	}
	PuppyList(puppiesState)
}

@Composable
private fun PuppyList(puppiesState: SnapshotStateList<PuppyModel>) {
	val groupedPuppies = puppiesState.groupBy {
		it.breed
	}

	val expansionState = remember {
		mutableStateMapOf<Breed, Boolean>().apply {
			Breed.values().forEach { breed ->
				this[breed] = true
			}
		}
	}

	val toggleAdoption: (PuppyModel) -> Unit = { puppy ->
		// assignment to puppies[i] calls the set() method of SnapshotStateList,
		// that re-evaluates the equals of the list,
		// that re-evaluates the equals of each item in the list,
		// and that triggers a recomposition, showing the updated PuppyItem

		val pos = puppiesState.indexOfFirst {
			it.id == puppy.id
		}
		puppiesState[pos] = puppiesState[pos].apply {
			adopted = !adopted
		}
	}

	val toggleExpansion: (Breed) -> Unit = { breed ->
		expansionState[breed] = !expansionState.getOrDefault(breed, true)
	}

	LazyColumn {
		groupedPuppies.entries
			.sortedBy { (breed, _) ->
				breed.name
			}.forEach { (breed, puppies) ->
				val expanded = expansionState.getOrDefault(breed, true)
				item {
					PuppyBreedItem(breed, expanded, toggleExpansion)
				}
				if (expanded) {
					itemsIndexed(puppies) { idx, puppy ->
						PuppyItem(puppy, idx == puppies.lastIndex, toggleAdoption)
					}
				}
			}
	}
}

private data class PuppyModel(
	val id: Int,
	val name: String = "Dog $id",
	val breed: Breed = Breed.random(),
	var adopted: Boolean = id.rem(4) == 0,
)

private enum class Breed {
	Beagle, Chihuahua, Dalmatian, Dobermann, GermanShepherd, Husky, TibetanSpaniel;

	companion object {
		fun random() = values()[Random.nextInt(values().size)]
	}
}

@Composable
private fun PuppyBreedItem(breed: Breed, expanded: Boolean, onClick: (Breed) -> Unit) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.background(Color(0xffdddddd))
			.clickable {
				onClick(breed)
			}
			.padding(8.dp)
	) {
		Text(
			text = "\uD83D\uDC15 ${breed.name}",
			fontWeight = FontWeight.Bold,
			modifier = Modifier
				.weight(1f)
		)
		if (expanded) {
			Text(text = """🔼""")
		} else {
			Text(text = """🔽""")
		}
	}
}

@Composable
private fun PuppyItem(puppy: PuppyModel, last: Boolean, onClick: (PuppyModel) -> Unit) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(start = 16.dp, end = 16.dp, top = 15.dp, bottom = if (last) 16.dp else 0.dp)
			.clickable {
				onClick(puppy)
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
				text = "\uD83D\uDC36 ${puppy.name}",
				fontWeight = FontWeight.Bold,
				modifier = Modifier
					.fillMaxWidth()
			)
			Text(
				text = "${puppy.breed}" + if (puppy.adopted) ", adopted" else "",
				modifier = Modifier
					.fillMaxWidth()
			)
		}
	}
}
