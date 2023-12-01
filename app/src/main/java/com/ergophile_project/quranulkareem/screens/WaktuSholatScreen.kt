@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class
)

package com.ergophile_project.quranulkareem.screens

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ergophile_project.quranulkareem.R
import com.ergophile_project.quranulkareem.data.ItemWaktuSholat
import com.ergophile_project.quranulkareem.data.remote.model.ApiInterface
import com.ergophile_project.quranulkareem.data.remote.model.api_prayer_time.Time
import com.ergophile_project.quranulkareem.service.location.LocationService
import com.ergophile_project.quranulkareem.service.location.LocationServiceCondition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WaktuSholat() {
    val dateFlow = getTime().collectAsState(initial = "")

    val api = ApiInterface.createApi()

    val prayerTime = remember {
        mutableStateListOf<Time?>()
    }

    val context = LocalContext.current
    val client = LocationServices.getFusedLocationProviderClient(context)

    val locationTracker = LocationService(
        client,
        context
    )

    val locationState = remember {
        MutableStateFlow<LocationServiceCondition<Location?>?>(null)
    }

    val geoCoder = Geocoder(context)

    val locationPermission = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ),
    )

    if (!locationPermission.allPermissionsGranted) {
        LaunchedEffect(
            key1 = true
        ) {
            locationPermission.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(locationPermission.allPermissionsGranted) {
        locationState.emit(locationTracker.getCurrentLocation())
    }

    val gabunganListWaktuSholat = mutableListOf<ItemWaktuSholat>()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp, start = 16.dp),
                title = {
                    Text(
                        text = "Waktu Sholat",
                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_quranulkarem),
                        contentDescription = "Logo Quranul-Kareem",
                        modifier = Modifier.size(48.dp)
                    )
                }
            )
        }
    ) {
        val padding = it
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_on_24),
                            contentDescription = null
                        )
                        locationState.asStateFlow().collectAsState().let { state ->
                            when (val locationCondition = state.value) {
                                is LocationServiceCondition.Error -> {
                                    Text(
                                        text = "-",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                                is LocationServiceCondition.MissingPermission -> {
                                    Text(
                                        text = "-",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                                is LocationServiceCondition.NoGps -> {
                                    Text(
                                        text = "Gps Belum Aktif!",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }

                                is LocationServiceCondition.Success -> {
                                    val location = locationCondition.location
                                    val latitude = location?.latitude
                                    val longtitude = location?.longitude
                                    if (latitude != null && longtitude != null) {
                                        val locationName = geoCoder.getFromLocation(
                                            latitude, longtitude, 1
                                        )?.get(0)
                                        val kota = locationName?.subAdminArea
                                        val namaNegara = locationName?.countryName
                                        Text(
                                            text = "$kota, $namaNegara",
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    val listTextSholat = listOf(
                                        "Sholat Shubuh",
                                        "Sholat Dzuhur",
                                        "Sholat Ashar",
                                        "Sholat Maghrib",
                                        "Sholat Isya"
                                    )
                                    val listBackgroundWaktu = listOf(
                                        R.drawable.background_shubuh,
                                        R.drawable.background_dzuhur,
                                        R.drawable.background_ashar,
                                        R.drawable.background_maghrib,
                                        R.drawable.background_isya
                                    )

                                    LaunchedEffect(key1 = true) {
                                        val result = api.getPrayerTime(
                                            latitude = latitude.toString(),
                                            longitude = longtitude.toString()
                                        )
                                        prayerTime.clear()
                                        prayerTime.addAll(result.times)
                                    }

                                    if (prayerTime.isNotEmpty()) {
                                        gabunganListWaktuSholat.clear()
                                        for (i in listTextSholat.indices) {
                                            val dataJadwalSholat = ItemWaktuSholat(
                                                listTextSholat[i],
                                                listOf(
                                                    prayerTime[0]?.fajr,
                                                    prayerTime[0]?.dhuhr,
                                                    prayerTime[0]?.asr,
                                                    prayerTime[0]?.maghrib,
                                                    prayerTime[0]?.isha
                                                )[i].toString(),
                                                listBackgroundWaktu[i]
                                            )
                                            gabunganListWaktuSholat.add(dataJadwalSholat)
                                        }
                                    }
                                }

                                null -> {
                                    Box(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Mengambil lokasi...",
                                            color = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                text = dateFlow.value,
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontFamily = FontFamily(Font(R.font.monda_regular))
                            )
                        }
                    }
                }

                LazyVerticalGrid(
                    modifier = Modifier.height(500.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(gabunganListWaktuSholat) { itemWaktuSholat ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = painterResource(id = itemWaktuSholat.backgroundSholat),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = itemWaktuSholat.sholat,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = itemWaktuSholat.waktuSholat,
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.monda_regular)),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getTime() = flow {
    while (true) {
        val date = java.text.SimpleDateFormat(
            "HH:mm:ss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        emit(date)
        delay(1000)
    }
}