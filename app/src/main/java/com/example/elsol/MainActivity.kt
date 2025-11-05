package com.example.elsol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.elsol.ui.theme.ElSolTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.UUID

data class Photo (
    val id: UUID,
    val name: String,
    @DrawableRes val photo: Int
)
val photos = mutableListOf<Photo>(
    Photo(UUID.randomUUID(),"Corona solar", R.drawable.corona_solar),
    Photo(UUID.randomUUID(),"Erupción solar", R.drawable.erupcionsolar),
    Photo(UUID.randomUUID(),"Espículas", R.drawable.espiculas),
    Photo(UUID.randomUUID(),"Filamentos", R.drawable.filamentos),
    Photo(UUID.randomUUID(),"Magnetosfera", R.drawable.magnetosfera),
    Photo(UUID.randomUUID(),"Manchasolar", R.drawable.manchasolar)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElSolTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painter = painterResource(R.drawable.espiculas),
                                    contentDescription = "picture",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().height(200.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                NavigationDrawerItem(
                                    label = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Filled.Build, contentDescription = "Icon build")
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text("Build")
                                        }
                                    },
                                    selected = (currentRoute == "Main"),
                                    onClick = {
                                        navController.navigate("Main")
                                        scope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.padding(horizontal = 30.dp)
                                )

                                NavigationDrawerItem(
                                    label = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Filled.Info, contentDescription = "Icon info")
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text("Info")
                                        }
                                    },
                                    selected = (currentRoute == "Info"),
                                    onClick = {
                                        navController.navigate("Info")
                                        scope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.padding(horizontal = 30.dp)
                                )

                                NavigationDrawerItem(
                                    label = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Filled.Email, contentDescription = "Icon email")
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text("Email")
                                        }
                                    },
                                    selected = (currentRoute == "Email"),
                                    onClick = {},
                                    modifier = Modifier.padding(horizontal = 30.dp)
                                )
                            }
                        }
                    }
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { SnackbarHost(snackbarHostState) }, bottomBar = {  BottomAppBar(drawerState, scope) }) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "Main",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("Main") { MainScreen(modifier = Modifier.padding(innerPadding),snackbarHostState, scope) }
                            composable("Info") { InfoScreen(modifier = Modifier.padding(innerPadding)) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState, scope: CoroutineScope) {

    val photoState = remember { mutableStateOf(photos) }

    val expandedMapState = remember {
        photoState.value.mapIndexed { index, _ ->
            index to false
        }.toMutableStateMap()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(photoState.value.size) { index ->
            val photo = photoState.value[index]
            val expanded = expandedMapState[index] ?: false
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFdfd0ea)
                ),
                modifier = Modifier.clickable {
                    scope.launch {
                        snackbarHostState.showSnackbar(photo.name)
                    }
                }
            ) {
                Column {
                    Image(
                        painter = painterResource(id = photo.photo),
                        contentDescription = photo.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
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
                                        expandedMapState[index] = false
                                        val copy = photo.copy(id = UUID.randomUUID())
                                        photoState.value = (photoState.value + copy).toMutableList()
                                    },
                                    leadingIcon = { Icon(Icons.Filled.Add, "Add") },
                                    text = { Text("Copiar") }
                                )

                                DropdownMenuItem(
                                    onClick = {
                                        expandedMapState[index] = false
                                        /*photoState.value = photoState.value.toMutableList().apply {
                                            removeAt(index)
                                        }*/

                                        val newList = photoState.value.toMutableList()
                                        newList.removeAt(index)
                                        photoState.value = newList
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

@Composable
fun BottomAppBar(drawerState: DrawerState, scope: CoroutineScope) {

    var contador by remember { mutableStateOf(0) }

    BottomAppBar(
        containerColor = Color(0xFFf03305),
        actions = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Localized description", tint = Color.White)
            }
            IconButton(onClick = { contador++ }) {
                BadgedBox(badge = {
                    if (contador > 0) {
                        Badge {
                            Text(contador.toString())
                        }
                    }
                }) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}


/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElSolTheme {
        MainScreen()
    }
}*/