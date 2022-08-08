package DI

import Model.CountriesApi
import Model.CountriesService
import ViewModel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponents {
    fun inject(service: CountriesService)

    fun inject(viewModel: ListViewModel)
}