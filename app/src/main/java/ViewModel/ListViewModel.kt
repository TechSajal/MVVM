package ViewModel

import Model.Country
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel:ViewModel(){
   val Countries = MutableLiveData<List<Country>>()
    val CountryLoadError = MutableLiveData<Boolean>()
    val Loading = MutableLiveData<Boolean>()
    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries() {
        val mockdata = listOf(Country("CountryA"),
            Country("CountryB"),
            Country("CountryC"),
            Country("CountryD"),
            Country("CountryE"),
            Country("CountryF"),
            Country("CountryG"),
            Country("CountryH"),
            Country("CountryI"),
            Country("CountryJ"),
            Country("CountryK"),
            Country("CountryL"),
            Country("CountryM"),
            Country("CountryN"),
            Country("CountryO"),
            Country("CountryP"),
            Country("CountryQ"),
            Country("CountryR"))
        Countries.value = mockdata
        Loading.value=false
        CountryLoadError.value = false
    }

}