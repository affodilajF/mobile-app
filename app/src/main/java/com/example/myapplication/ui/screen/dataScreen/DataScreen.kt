package com.example.myapplication.ui.screen.dataScreen

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.myapplication.data.local.model.PersonEntity
import com.example.myapplication.ui.components.InformationCard
import com.example.myapplication.ui.components.VoterData
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.screen.AppViewModel


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataScreen(navController: NavController){

    val expandedState = remember { mutableStateOf(mutableMapOf<Long, Boolean>()) }
    val appViewModel: AppViewModel = hiltViewModel()
    val persons by appViewModel.persons.collectAsState()

    LaunchedEffect(Unit) {
        appViewModel.fetchAllPersons()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "There are the data!",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFF6200EE) // Warna ungu gelap
                )
            )
        },



        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {


                Column {
//                    Button(
//                        onClick = {
//                            appViewModel.deleteALlPerson()
//                        },
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .align(Alignment.End)
//                    ) {
//                        Text("Delete All Data")
//                    }

                    LazyColumn(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                        items(persons, key = { it.id }) { item ->
                            VoterData(item, expandedState)
                        }
                    }
                }
            }
        }
    )
}


@Preview()
@Composable
fun DataScreenPreview() {
    val navController = rememberNavController()
    DataScreen(navController = navController)
}

