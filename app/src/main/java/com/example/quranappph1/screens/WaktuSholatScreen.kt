@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class,
    ExperimentalPermissionsApi::class
)

package com.example.quranappph1.screens

import android.location.Geocoder
import android.location.Location
import android.util.Log
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quranappph1.R
import com.example.quranappph1.data.ItemKalenderIslam
import com.example.quranappph1.data.ItemWaktuSholat
import com.example.quranappph1.data.remote.model.ApiInterface
import com.example.quranappph1.data.remote.model.api_prayer_time.GetPrayerTimeSchedule
import com.example.quranappph1.data.remote.model.api_prayer_time.Time
import com.example.quranappph1.service.location.LocationService
import com.example.quranappph1.service.location.LocationServiceCondition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Preview
@Composable
fun preview() {
    WaktuSholat()
}

@Composable
fun WaktuSholat() {
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


    var switchToggle by remember {
        mutableStateOf(false)
    }

    val gabunganListWaktuSholat = mutableListOf<ItemWaktuSholat>()

    val listGambarTanggal = listOf(
        R.drawable.isra_miraj,
        R.drawable.nisfu_syaban,
        R.drawable.lailatul_qadr,
        R.drawable.idul_fitr,
        R.drawable.idul_adha,
        R.drawable.muharram,
        R.drawable.asyura,
        R.drawable.rabiulawal
    )

    val listNamaHari = listOf(
        "Isra Mi'raj",
        "Nisyfu Sya’ban",
        "Lailatul Qadr",
        "Idul Fitri",
        "Idul Adha",
        "Muharram",
        "Ashyura",
        "12 Rabiul Awwal"
    )
    val listTanggalHari = listOf(
        "Senin, 27 Rajab 1444 H",
        "Rabu, 15 Sya’ban 1444 H",
        "Selasa, 27 Ramadhan 1444 H ",
        "Jum’at, 1 Syawal 1444 H",
        "Rabu, 10 Dzulhijjah 1444 H",
        "Rabu, 1 Muharram 1445 H",
        "Kamis, 9 Muharram 1445 H",
        "Rabu, 12 Rabi’ul Awwal 1445 H"
    )
    val gabunganListKalender = mutableListOf<ItemKalenderIslam>()
    for (i in listNamaHari.indices) {
        val dataKalender = ItemKalenderIslam(
            listNamaHari[i],
            listTanggalHari[i],
            listGambarTanggal[i]
        )
        gabunganListKalender.add(dataKalender)
    }


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
                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_on_24),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(8.dp))
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

                LazyVerticalGrid(
                    modifier = Modifier.height(300.dp),
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
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = """
                    Hari Besar Islam 2023
                    1444H-1445H
                """.trimIndent(),
                    fontFamily = FontFamily(Font(R.font.monda_regular)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            items(gabunganListKalender) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = item.gambarTanggal),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.size(12.dp))
                    Column {
                        Text(
                            text = item.namaHari,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.monda_regular))
                        )
                        Text(
                            text = item.tanggalHari,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = FontFamily(Font(R.font.monda_regular))
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

    }
}