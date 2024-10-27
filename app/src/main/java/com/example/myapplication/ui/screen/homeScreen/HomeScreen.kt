package com.example.myapplication.ui.screen.homeScreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.ui.components.InformationCard
import com.example.myapplication.ui.navigation.Routes
import com.google.android.gms.maps.MapView
import com.google.firebase.firestore.GeoPoint

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, onExit: () -> Unit){
    var expanded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (expanded) 1.1f else 1f, animationSpec = tween(durationMillis = 300))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Welcome!",
                        style = MaterialTheme.typography.titleLarge,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = androidx.compose.ui.graphics.Color.White)
                    }
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Logout", tint = androidx.compose.ui.graphics.Color.White)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFF6200EE) // Warna ungu gelap
                )
            )
        },

        floatingActionButton = {
            if (expanded) {
                // Two FAB when expanded
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Text("Add Data", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Black)
                        Spacer(modifier = Modifier.width(8.dp)) // Jarak antara FAB dan teks
                        FloatingActionButton(
                            onClick = { navController.navigate(Routes.FORM_SCREEN) },
                            containerColor = Color(0xFF8253C5)
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Add", tint = androidx.compose.ui.graphics.Color.White)
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Text("Exit", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        FloatingActionButton(
                            onClick = { onExit() },
                            containerColor = Color(0xFF9C7ACC)
                        ) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Add", tint = androidx.compose.ui.graphics.Color.White)
                        }
                    }

                    FloatingActionButton(
                        onClick = { expanded = false },
                        containerColor = MaterialTheme.colorScheme.error,
                        modifier = Modifier.height(35.dp).width(35.dp).scale(scale)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            } else {
                // One FAB
                FloatingActionButton(
                    onClick = { expanded = true },
                    containerColor = Color(0xFF6200EE),
                    contentColor = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.scale(scale)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        },


        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // INFORMATION BUTTON
                    InformationCard(
                        informationText = "Key details about upcoming elections and voting procedures.",
                        buttonText = "See Election Information",
                        icon = Icons.Filled.CheckCircle,
                        onClick = {
                            navController.navigate(Routes.ELECTION_INFO_SCREEN)
                        }
                    )

                    // LIHAT DATA
                    InformationCard(
                        informationText = "Accessing voter informations.",
                        buttonText = "See Voters",
                        icon = Icons.Filled.Person,
                        onClick = {
                            navController.navigate(Routes.DATA_SCREEN)
                        }
                    )



                }
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview(){
    val navController = rememberNavController() // Dummy NavController
//    HomeScreen(navController)
}




