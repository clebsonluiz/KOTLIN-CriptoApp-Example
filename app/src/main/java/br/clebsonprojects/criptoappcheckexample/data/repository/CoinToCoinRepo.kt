package br.clebsonprojects.criptoappcheckexample.data.repository

import br.clebsonprojects.criptoappcheckexample.data.remote.APIConstants
import br.clebsonprojects.criptoappcheckexample.data.remote.ApiWorkerClient
import br.clebsonprojects.criptoappcheckexample.data.remote.CoinToCoinAPI
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.DataBrlToCoinDTO
import br.clebsonprojects.criptoappcheckexample.data.remote.dto.DataCoinToBrlDTO
import retrofit2.*


class CoinToCoinRepo  {


    private fun getData(): CoinToCoinAPI {
        return Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ApiWorkerClient.gsonConverter)
            .client(ApiWorkerClient.client)
            .baseUrl(APIConstants.BASE_URL)
            .build()
            .create(CoinToCoinAPI::class.java)
    }


    suspend fun getCoinToBrl(): DataCoinToBrlDTO?{
        var bodyData: DataCoinToBrlDTO? = null

        try {
            val retrofitData = getData().getCoinToBRL()

            bodyData = retrofitData.await()

//            retrofitData.enqueue(object : Callback<DataCoinToBrlDTO?> {
//                override fun onResponse(
//                    call: Call<DataCoinToBrlDTO?>,
//                    response: Response<DataCoinToBrlDTO?>
//                ) {
//                    bodyData = response.body()
//                    println(bodyData)
//                }
//
//                override fun onFailure(call: Call<DataCoinToBrlDTO?>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            })

        }catch (e: Exception){
            print(e.message)
        }
        return bodyData
    }

//    suspend fun getBrlToCoin(): DataBrlToCoinDTO?{
//        var bodyData: DataBrlToCoinDTO? = null
//
//        try {
//            val retrofitData = getData().getBRLToCoin()
//            bodyData = retrofitData.await()
////            retrofitData.enqueue(object : Callback<DataBrlToCoinDTO?> {
////                override fun onResponse(
////                    call: Call<DataBrlToCoinDTO?>,
////                    response: Response<DataBrlToCoinDTO?>
////                ) {
////                    bodyData = response.body()
////
////                }
////
////                override fun onFailure(call: Call<DataBrlToCoinDTO?>, t: Throwable) {
////                    TODO("Not yet implemented")
////                }
////            })
//
//        }catch (e: Exception){
//            print(e.message)
//        }
//        return bodyData
//    }
}