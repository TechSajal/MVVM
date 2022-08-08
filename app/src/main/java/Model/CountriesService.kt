package Model


import DI.DaggerApiComponents
import dagger.android.DaggerApplication
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class CountriesService {

 @Inject
 lateinit var api:CountriesApi
    init {
       DaggerApiComponents.create().inject(this)
    }

    fun getCountries():Single<List<Country>>{
     return api.getCountries()
 }
}