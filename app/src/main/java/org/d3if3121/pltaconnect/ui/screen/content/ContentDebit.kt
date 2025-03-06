package org.d3if3121.pltaconnect.ui.screen.content

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.d3if3121.pltaconnect.components.LoadingIndicator
import org.d3if3121.pltaconnect.core.printError
import org.d3if3121.pltaconnect.data.model.Response.Failure
import org.d3if3121.pltaconnect.data.model.Response.Loading
import org.d3if3121.pltaconnect.data.model.Response.Success
import org.d3if3121.pltaconnect.data.model.data.Debit
import org.d3if3121.pltaconnect.data.repository.ImportData
import org.d3if3121.pltaconnect.navigation.Screen
import org.d3if3121.pltaconnect.ui.component.ButtonMerah
import org.d3if3121.pltaconnect.ui.component.ButtonTiga
import org.d3if3121.pltaconnect.ui.component.DataDua
import org.d3if3121.pltaconnect.ui.component.DialogLoading
import org.d3if3121.pltaconnect.ui.component.InputPutihKeterangan
import org.d3if3121.pltaconnect.ui.component.JudulUtama
import org.d3if3121.pltaconnect.ui.theme.Warna
import org.d3if3121.pltaconnect.ui.viewmodel.PegawaiListViewModel


@Composable
fun ContentDebit(
    tanggal: String = "Tanggal Kosong",
    lazyListState: LazyListState,
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    viewmodel: PegawaiListViewModel = hiltViewModel(),
    navController: NavHostController
){
    DialogLoading(viewmodel)

    var judul2 by remember { mutableStateOf("(Jam 24)") }
    var context = LocalContext.current

    DebitSungaiResponse(
        context = context,
        viewmodel = viewmodel,
        navController = navController
    )

    JudulUtama(
        judul1 = "Debit Sungai",
        judul2 = judul2,
        tanggal = tanggal,

        onClickBack = onClickBack,
        onClickNext = onClickNext,
        onJudul2Change = {
            judul2 = it
        }
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .background(color = Warna.PutihNormal),
        state = lazyListState
    ){
        item {
            Spacer(modifier = Modifier.padding(bottom = 6.dp))
            MainContentDebit(viewmodel, tanggal, judul2)
        }

    }
}
@Composable
fun MainContentDebit(
    viewmodel: PegawaiListViewModel = hiltViewModel(),
    tanggal: String,
    judul2: String
){
    val id = tanggal + "_debit_" + judul2
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.getDebit(id)

    }


    var isClicked by remember { mutableStateOf(false) }

    var maksimal by remember { mutableStateOf("") }
    var minimal by remember { mutableStateOf("") }
    var ratarata by remember { mutableStateOf("") }

    var dam by remember { mutableStateOf("") }
    var kth by remember { mutableStateOf("") }
    var ph by remember { mutableStateOf("") }

    var maxdam by remember { mutableStateOf("") }
    var mindam by remember { mutableStateOf("") }

    var tma by remember { mutableStateOf("") }

    GetDebitSungaiResponse(
        context = context,
        viewmodel = viewmodel
    ){ data ->
        maksimal = data.maksimal
        minimal = data.minimal
        ratarata = data.rata2
        dam = data.dam
        kth = data.kth
        ph = data.ph
        maxdam = data.maxdam
        mindam = data.mindam
        tma = data.tma
    }



    Card(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight(),
        colors = CardDefaults.cardColors(containerColor = Warna.PutihNormal),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){

        Column (
            modifier = Modifier.padding(17.dp).fillMaxWidth()
        ){
            DataDua(
                tigacond = true,
                judul1 = "Debit Sungai Cicatih",
                judul2 = "Rata-rata:",
                warnarata1 = Warna.MerahNormal,
                ratarata = ratarata,
                text1k1 = "Maksimal:",
                text2k1 = "",
                text1k2 = "Minimal:",
                text2k2 = "",
                text1k3 = "Rata-rata:",
                hasil1 = maksimal,
                onHasil1Change = {
                    maksimal = it
                },
                hasil2 = minimal,
                onHasil2Change = {
                    minimal= it
                } ,
                hasil3 = ratarata,
                onHasil3Change = {
                    ratarata= it
                } ,
            )

            DataDua(
                tigacond = true,
                judul1 = "Curah Hujan",
                text1k1 = "DAM:",
                text2k1 = "mm",
                text1k2 = "KTH:",
                text2k2 = "mm",
                text1k3 = "PH:",
                text2k3 = "mm",
                hasil1 = dam,
                onHasil1Change = {
                    dam = it
                },
                hasil2 = kth,
                onHasil2Change = {
                    kth = it
                } ,
                hasil3 = ph,
                onHasil3Change = {
                    ph = it
                } ,
            )

            DataDua(
                judul1 = "Diatas DAM",
                text1k1 = "Max:",
                text2k1 = "cm",
                text1k2 = "Min:",
                text2k2 = "cm",

                hasil1 = maxdam,
                onHasil1Change = {
                    maxdam = it
                },
                hasil2 = mindam,
                onHasil2Change = {
                    mindam = it
                } ,
            )

            InputPutihKeterangan(
                text1 = "TMA Rata-rata KTH :",
                inputan = tma,
                onInputanChange = {
                    tma = it
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )



        }

    }

    ButtonMerah(
        onClick = {
            if (!isClicked) {
                isClicked = true
                viewmodel.addDebit(
                    Debit(
                        id = id,
                        tanggal = tanggal,
                        maksimal = maksimal,
                        minimal = minimal,
                        dam = dam,
                        kth = kth,
                        ph = ph,
                        maxdam = maxdam,
                        mindam = mindam,
                        tma = tma,
                        rata2 = ratarata
                    )
                )
            }

        },
        modifier = Modifier.fillMaxWidth().padding(top = 18.dp).height(46.dp),
        content = {
            Text(
                text = "KIRIM",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp,
                color = Warna.PutihNormal
            )
        }
    )


    Spacer(modifier = Modifier.height(150.dp))
}



@Composable
fun DebitSungaiResponse(context: Context, viewmodel: PegawaiListViewModel, navController: NavHostController){
    when(val addRequestResponse = viewmodel.addDebitResponse){
        is Loading -> {
        }
        is Success -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            val hasilimport = ImportData(viewmodel, addRequestResponse.idsheet!!)
            Log.e("firestore", addRequestResponse.toString())

            if (hasilimport == "Import sukses!"){
                viewmodel.changeLoading(false)
                viewmodel.addDebitResponseReset()
                navController.navigate(Screen.Home.route)
            } else {
                Log.e("firestore", hasilimport)
            }
        }
        is Failure -> {
            Toast.makeText(context, addRequestResponse.toString(), Toast.LENGTH_SHORT).show()
            Log.e("firestore", addRequestResponse.e.toString())
        }
    }

}


@Composable
fun GetDebitSungaiResponse(context: Context, viewmodel: PegawaiListViewModel, action: (Debit) -> Unit){
    when(val response = viewmodel.getDebitResponse){
        is Loading -> {
        }
        is Success -> {
            response.data?.let {
                action(it)
                viewmodel.changeLoading(false)
            }
        }

        is Failure -> {
            viewmodel.changeLoading(false)

        }
    }

}