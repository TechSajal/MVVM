package com.example.mvvm

import Model.CountriesService
import Model.Country
import ViewModel.ListViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var countriesService: CountriesService

    @InjectMocks
    val listViewModel = ListViewModel()

    private var testSingle: Single<List<Country>>?=null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getCountriesSuccess(){
        val country = Country("countryname","capital","url")
        val countriesLists = arrayListOf(country)
        testSingle = Single.just(countriesLists)
        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()
        Assert.assertEquals(1  ,listViewModel.Countries.value?.size)
        Assert.assertEquals(false,listViewModel.CountryLoadError.value)
        Assert.assertEquals(false,listViewModel.Loading.value)
    }

    @Test
    fun getCountriesFail(){
        testSingle = Single.error(Throwable())
        Mockito.`when`(countriesService.getCountries()).thenReturn(testSingle)
        listViewModel.refresh()
        Assert.assertEquals(true,listViewModel.CountryLoadError.value)
        Assert.assertEquals(false,listViewModel.Loading.value)
    }


    @Before
    fun setupRxSchedulers(){
        val immediate = object:Scheduler(){
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }

        }
        RxJavaPlugins.setInitIoSchedulerHandler{ _ -> immediate}
        RxJavaPlugins.setInitComputationSchedulerHandler { _ -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { _ -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { _ -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> immediate }
    }
}