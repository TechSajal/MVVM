package View

import ViewModel.ListViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ListViewModel
    private val countriesadapter = CountryListAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      viewModel = ViewModelProviders.of(this)[ListViewModel::class.java]
        viewModel.refresh()
        findViewById<RecyclerView>(R.id.countriesList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesadapter
        }
        observeviewmodel()
    }

    private fun observeviewmodel() {
        viewModel.Countries.observe(this, Observer{countries ->
            countries?.let { countriesadapter.updateCountries(it) }
        })
        viewModel.CountryLoadError.observe(this,Observer{iserror ->
            iserror?.let { findViewById<TextView>(R.id.list_error).visibility =if(it) View.VISIBLE else View.GONE}
        })
        viewModel.Loading.observe(this,Observer{isloading ->
            isloading?.let {
                findViewById<ProgressBar>(R.id.loading_view).visibility = if (it)View.VISIBLE else View.GONE
                if (it){
                    findViewById<TextView>(R.id.list_error).visibility = View.GONE
                    findViewById<RecyclerView>(R.id.countriesList).visibility = View.GONE
                }
            }
        })
    }
}