package com.example.elsol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.elsol.ui.theme.ElSolTheme

data class Photo (
    val name: String,
    @DrawableRes val photo: Int
)

val photos = listOf<Photo>(
    Photo("Corona solar", R.drawable.corona_solar),
    Photo("Erupción solar", R.drawable.erupcionsolar),
    Photo("Espículas", R.drawable.espiculas),
    Photo("Filamentos", R.drawable.filamentos),
    Photo("Magnetosfera", R.drawable.magnetosfera),
    Photo("Manchasolar", R.drawable.manchasolar)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElSolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    //val photosState by remember { mutableStateOf(photos) }
    val photoState = remember { mutableStateListOf<Photo>() }
    photoState.addAll(photos)

    val expandedMapState = remember {
        photoState.mapIndexed { index, photo ->
            index to false
        }.toMutableStateMap()
    }

    val optionState = remember {
        photoState.mapIndexed { index, photo ->
            index to ""
        }.toMutableStateMap()
    }

    //var option by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(photoState.size) { index ->
            val photo = photoState[index]
            //var expanded by remember { mutableStateOf(false) }
            val expanded = expandedMapState[index] ?: false
            val option = optionState[index] ?: ""
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFdfd0ea)
                ),
                modifier = Modifier
            ) {
                Column {
                    Image(
                        painter = painterResource(id = photo.photo),
                        contentDescription = photo.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )

                    //Spacer(modifier = Modifier.size(10.dp))

                    Row(modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = photo.name,
                            fontSize = 14.sp
                        )

                        Box() {
                            IconButton(
                                onClick = { expandedMapState[index] = !(expandedMapState[index] ?: false) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "icon",
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expandedMapState[index] = false },
                                modifier = Modifier.background(Color(0xFFeee5f4))
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        optionState[index] = "Copiar"
                                        expandedMapState[index] = false
                                    },
                                    leadingIcon = { Icon(Icons.Filled.Add, "Add") },
                                    text = { Text("Copiar") }
                                )

                                DropdownMenuItem(
                                    onClick = {
                                        optionState[index] = "Eliminar"
                                        expandedMapState[index] = false
                                    },
                                    leadingIcon = { Icon(Icons.Filled.Delete, "Delete") },
                                    text = { Text("Eliminar") }
                                )
                            }
                        }


                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElSolTheme {
        MainScreen()
    }
}