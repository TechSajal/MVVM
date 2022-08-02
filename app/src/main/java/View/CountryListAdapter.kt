package View

import Model.Country
import Utils.getProgressDrawble
import Utils.loadImage
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.mvvm.R


class CountryListAdapter(var countries:ArrayList<Country>):RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>(){
    fun updateCountries(newCountries:List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int)=CountryViewHolder (
               LayoutInflater.from(parent.context).inflate(R.layout.item_country,parent,false)
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() =  countries.size



    class CountryViewHolder(view: View):RecyclerView.ViewHolder(view){
     private val countryName:TextView = view.findViewById(R.id.name)
        private val capital:TextView = view.findViewById(R.id.capital)
        private val flag:ImageView = view.findViewById(R.id.imageView)
        private val progressDrawable = getProgressDrawble(view.context)
       fun bind(country: Country){
            countryName.text = country.countryName
            capital.text = country.capital
           flag.loadImage(country.flag,progressDrawable)

       }
    }
}


