package br.clebsonprojects.criptoappcheckexample.data.remote.dto

data class CoinDTO(
    val ask: String?,
    val bid: String?,
    val code: String?,
    val codein: String?,
    val create_date: String?,
    val high: String?,
    val low: String?,
    val name: String?,
    val pctChange: String?,
    val timestamp: String?,
    val varBid: String?
){


    companion object {
        fun empty(): CoinDTO {
            return CoinDTO(
                code = "",
                ask = "0",
                codein = "",
                name = "",
                create_date = "0",
                pctChange = "0",
                bid = "0",
                high = "0",
                low = "0",
                timestamp = "0",
                varBid = "0"
            )
        }
    }

    fun parseToHigh(multiplier: Double?): CoinDTO {
        return CoinDTO(
            code = code,
            ask = (ask!!.toDouble() * multiplier!!).toString(),
            codein = codein,
            name = name,
            create_date = create_date,
            pctChange = pctChange,
            bid = (bid!!.toDouble() * multiplier).toString(),
            high = (high!!.toDouble() * multiplier).toString(),
            low = (low!!.toDouble() * multiplier).toString(),
            timestamp = timestamp,
            varBid = varBid
        )
    }

}