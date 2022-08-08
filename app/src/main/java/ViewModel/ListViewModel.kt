package ViewModel

import DI.DaggerApiComponents
import Model.CountriesService
import Model.Country
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import javax.inject.Inject


class ListViewModel:ViewModel(){
     @Inject
    lateinit var countriesService :CountriesService
    init {
        DaggerApiComponents.create().inject(this)
    }
    private val disposable = CompositeDisposable()
    val Countries = MutableLiveData<List<Country>>()
    val CountryLoadError = MutableLiveData<Boolean>()
    val Loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchCountries()
    }

    private fun fetchCountries() {
        Loading.value = true
        disposable.add(
            countriesService.getCountries()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(),
                    SingleObserver<List<Country>>, Disposable {
                    override fun onSuccess(value: List<Country>?) {
                            Countries.value = value
                            CountryLoadError.value = false
                           Loading.value = false
                    }
                    override fun onError(e: Throwable?) {
                            CountryLoadError.value = true
                            Loading.value = false
                    }

                    override fun onSubscribe(d: Disposable?) {

                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}