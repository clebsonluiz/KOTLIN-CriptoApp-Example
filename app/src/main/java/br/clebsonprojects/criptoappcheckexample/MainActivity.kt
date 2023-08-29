package br.clebsonprojects.criptoappcheckexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.CoinDTO
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.CoinData
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.DataBrlToCoinDTO
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.DataCoinToBrlDTO
import br.clebsonprojects.criptoappcheckexample.data.repository.CoinToCoinRepo
import br.clebsonprojects.criptoappcheckexample.ui.theme.CriptoAppCheckExampleTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CriptoAppCheckExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView()
                }
            }
        }

    }
}


private fun getData(coinData:  MutableState<CoinData?>){
    CoroutineScope(Dispatchers.IO).launch {

            val data: DataCoinToBrlDTO? = CoinToCoinRepo().getCoinToBrl()
            if(data != null){
                coinData.value = data.toCoinData()
            }
    }

}

//@Preview(showBackground = true)
@Composable
fun MainView() {

    val coinData = rememberSaveable {
        mutableStateOf<CoinData?>(
            null
        )
    }

    getData(coinData)

    Scaffold(
        floatingActionButton = {
            RefreshFAB {
                getData(coinData)
            }
        }
    ) {

        Card() {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                CriptoCard(
                    coin = if (coinData.value != null) coinData.value?.dol!! else CoinDTO.empty(),
                    R.drawable.cripto_usdt
                )
                CriptoCard(
                    coin = if (coinData.value != null) coinData.value!!.btc!! else CoinDTO.empty(),
                    R.drawable.cripto_bitcoin
                )
                CriptoCard(
                    coin = if (coinData.value != null)coinData.value!!.eth!! else CoinDTO.empty(),
                    R.drawable.cripto_etherium
                )
                CriptoCard(
                    coin = if (coinData.value != null)coinData.value!!.doge!! else CoinDTO.empty(),
                    R.drawable.cripto_dogecoin
                )
            }
        }

    }
}



@Composable
fun CriptoCard(coin: CoinDTO, imgId: Int) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Gray),
        ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = imgId),
                contentDescription = "",
                modifier = Modifier
                    .size(64.dp)
            )
            Column(Modifier.padding(0.dp)) {
                Text(text = coin.name!!, style = TextStyle(), fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),

                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(text = coin.code!!, fontWeight = FontWeight.Bold)
                        Text(text = ((1 * 100.0).roundToInt() / 100.0).toString(), fontWeight = FontWeight.Bold)
                    }
                    Card(
                        border = BorderStroke(2.dp, Color.Gray),
                        modifier = Modifier
                            .padding(5.dp)
                            .height(8.dp)
                            .fillMaxWidth((0.5f)),

                        ) {

                    }
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(text = coin.codein!!, fontWeight = FontWeight.Bold)
                        Text(text = ((coin.ask!!.toDouble() * 100.0).roundToInt() / 100.0).toString(), fontWeight = FontWeight.Bold)
                    }
                }
                Row(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,) {
                    Text(text = coin.create_date!!, style = TextStyle(), fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic, textAlign = TextAlign.End)
                    Row(modifier = Modifier
                        .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = coin.pctChange!! + "%", style = TextStyle(
                            color = if (coin.pctChange!!.toDouble() > 0) Color.Green else Color.Red
                        ), fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
                        Text(text = if (coin.pctChange!!.toDouble() > 0) " ⇑⇑" else " ⇓⇓", style = TextStyle(
                            color = if (coin.pctChange!!.toDouble() > 0) Color.Green else Color.Red
                        ), fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal, textAlign = TextAlign.Center, fontSize = 24.sp)


                    }
                }

            }
        }
    }
}


@Composable
fun RefreshFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,

        ) {
        Icon(Icons.Rounded.Refresh, contentDescription = "Refresh")
    }
}