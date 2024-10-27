package com.example.myapplication.ui.screen.formScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.myapplication.data.local.model.PersonEntity
import com.example.myapplication.ui.components.DatePickerModal
import com.example.myapplication.ui.components.PersonDialog
import com.example.myapplication.ui.navigation.Routes
import com.example.myapplication.ui.screen.AppViewModel
import com.example.myapplication.utils.createImageFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.migration.CustomInjection.inject
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController) {


    val appViewModel: AppViewModel = hiltViewModel()
    val context = LocalContext.current

    var nik by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var showDatePicker by remember { mutableStateOf(false) }
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    var gender by remember { mutableStateOf("") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val showSuccess by appViewModel.showSuccess.collectAsState()
    val isPersonInDatabase by appViewModel.isPersonInDatabase.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val photoFile = remember { mutableStateOf<File?>(null) }


    // Open Gallery
    val getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri.value = uri
        uri?.let {
            Log.d("Selected Image URI", it.toString())
        }
    }

    // Open Camera
    val takePictureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            photoFile.value?.let {
                bitmap = BitmapFactory.decodeFile(it.absolutePath)
                imageUri.value = Uri.fromFile(it)
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            photoFile.value = createImageFile(context)
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                photoFile.value!!
            )
            takePictureLauncher.launch(uri)
        }
    }

    var location by remember { mutableStateOf<Location?>(null) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)



    fun resetForm() {
        nik = TextFieldValue("")
        name = TextFieldValue("")
        phone = TextFieldValue("")
        date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        address = TextFieldValue("")
        gender = ""
        imageUri.value = null
        bitmap = null
    }

    LaunchedEffect( showSuccess) {
        if (showSuccess) {
            snackbarHostState.showSnackbar("Data processed")
            appViewModel.falseSuccess()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Form Input", color = Color.White) },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isPersonInDatabase != null) {
                PersonDialog(person = isPersonInDatabase) { appViewModel.resetIsPersonInDatabase() }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TextField(
                    value = nik,
                    onValueChange = { nik = it },
                    label = { Text("NIK") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("No. HP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Gender", style = MaterialTheme.typography.titleMedium)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // F
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { gender = "F" }
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = (gender == "F"),
                                onClick = { gender = "F" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6200EE)) // Warna yang menarik
                            )
                            Text("F", modifier = Modifier.padding(start = 4.dp))
                        }

                        // M
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { gender = "M" }
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = (gender == "M"),
                                onClick = { gender = "M" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6200EE))
                            )
                            Text("M", modifier = Modifier.padding(start = 4.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))


                TextField(
                    value = date,
                    onValueChange = {},
                    label = { Text("Date (yyyy-mm-dd)") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { getContent.launch("image/*") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7159DD),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Open Gallery")
                    }
                    Button(
                        onClick = {
                            when {
                                ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                                    photoFile.value = createImageFile(context)
                                    val uri = FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        photoFile.value!!
                                    )
                                    takePictureLauncher.launch(uri)
                                }
                                else -> {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }

                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7159DD),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Open Camera")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))


                imageUri.value?.let { uri ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Selected Image:")
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(uri)
                                    .apply {
                                        crossfade(true)
                                    }
                                    .build()
                            ),
                            contentDescription = "Selected Image",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        appViewModel.savePerson(
                            nik.text,
                            name.text,
                            phone.text,
                            date,
                            address.text,
                            gender,
                            imageUri.value?.toString()
                        )
                        resetForm()
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Check, contentDescription = "Submit")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit")
                }
            }
        }

        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = { selectedDateMillis ->
                    selectedDateMillis?.let {
                        date = LocalDate.ofEpochDay(it / (1000 * 60 * 60 * 24))
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    }
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun FormScreenPreview() {
    // FormScreen(navController = rememberNavController())
}


//private fun getLastLocation(
//    fusedLocationClient: FusedLocationProviderClient,
//    onLocationReceived: (Location?) -> Unit
//) {
//    if (ActivityCompat.checkSelfPermission(
//            LocalContext.current,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//        onLocationReceived(null)
//        return
//    }
//    fusedLocationClient.lastLocation.addOnSuccessListener { loc: Location? ->
//        onLocationReceived(loc)
//    }
//}



































