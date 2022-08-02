package Model

import io.reactivex.Single
import retrofit2.http.GET

interface CourtiesApi {
    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
}