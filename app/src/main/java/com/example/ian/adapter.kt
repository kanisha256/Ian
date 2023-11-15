package com.example.ian

import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.*

class adapter(private val context: Context,var datalist: ArrayList<model?>?) :
    RecyclerView.Adapter<adapter.myviewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.singlerow, parent, false)
        return myviewholder(view)
    }


    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        val data = datalist!![position]
        if (data!!.name == "Яндекс") {
            holder.imageView.setImageResource(R.drawable.yandex)
        }
        if (data!!.name == "Кинопоиск") {
            holder.imageView.setImageResource(R.drawable.kinopoisk)
        }
        if (data!!.name == "Okko") {
            holder.imageView.setImageResource(R.drawable.okko)
        }
        if (data!!.name == "Youtube") {
            holder.imageView.setImageResource(R.drawable.youtube)
        }
        if (data!!.name == "Netflix") {
            holder.imageView.setImageResource(R.drawable.netflix)
        }
        holder.t5.text = data.name
        holder.t6.text = "Цена: ${data.price} в месяц"
        holder.t3.text = data.date
    }

    override fun getItemCount(): Int {
        return datalist!!.size
    }


    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var t5: TextView
        var t6: TextView
        var t3: TextView
        var imageView: ImageView

        init {
            t5 = itemView.findViewById<TextView>(R.id.t5)
            t6 = itemView.findViewById<TextView>(R.id.t6)
            t3 = itemView.findViewById<TextView>(R.id.t3)
           imageView = itemView.findViewById(R.id.imageView)
        }
    }
}