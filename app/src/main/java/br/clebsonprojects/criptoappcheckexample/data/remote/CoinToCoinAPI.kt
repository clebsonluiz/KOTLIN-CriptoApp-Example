package br.clebsonprojects.criptoappcheckexample.data.remote

import br.clebsonprojects.criptoappcheckexample.data.remote.dto.DataBrlToCoinDTO
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.DataCoinToBrlDTO
import retrofit2.Call
import retrofit2.http.GET

interface CoinToCoinAPI {

    @GET(APIConstants.ROUTE_COIN_BRL)
    fun getCoinToBRL(): Call<DataCoinToBrlDTO>

//    @GET(APIConstants.ROUTE_BRL_COIN)
//    fun getBRLToCoin(): Call<DataBrlToCoinDTO>

}