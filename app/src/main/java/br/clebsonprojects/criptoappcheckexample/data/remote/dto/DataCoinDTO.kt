package br.clebsonprojects.criptoappcheckexample.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DataCoinToBrlDTO(
    @SerializedName(value = "USDBRL")
    val usdBRL: CoinDTO?,
    @SerializedName(value = "BTCUSD")
    val btcUSD: CoinDTO?,
    @SerializedName(value = "ETHUSD")
    val ethUSD: CoinDTO?,
    @SerializedName(value = "DOGEUSD")
    val dogeUSD: CoinDTO?
){



    fun toCoinData(): CoinData {

        return CoinData(
            dol = usdBRL,
            btc = btcUSD?.parseToHigh(1000.0),
            eth = ethUSD?.parseToHigh(1000.0),
            doge = dogeUSD
        )
    }
}

data class DataBrlToCoinDTO(
    @SerializedName(value = "BRLUSD")
    val brlUSD: CoinDTO?,
    @SerializedName(value = "BRLBTC")
    val brlBTC: CoinDTO?,
    @SerializedName(value = "BRLETH")
    val brlETH: CoinDTO?,
    @SerializedName(value = "BRLDOGE")
    val brlDOGE: CoinDTO?
){

    fun toCoinData(): CoinData {
        return CoinData(
            dol = brlUSD,
            btc = brlBTC,
            eth = brlETH,
            doge = brlDOGE
        )
    }
}