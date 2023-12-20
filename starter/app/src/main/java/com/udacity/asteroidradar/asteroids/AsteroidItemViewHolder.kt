package com.udacity.asteroidradar.asteroids

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.main.MainFragmentDirections

class AsteroidItemViewHolder(val itemBinding: AsteroidItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(item: Asteroid) {
        itemBinding.txtCodename.text = item.codename
        itemBinding.txtDay.text = item.closeApproachDate
        itemBinding.imgIcon.let {
            if (item.isPotentiallyHazardous) {
                it.setImageResource(R.drawable.ic_status_potentially_hazardous)
                it.contentDescription = "Icon potentially hazardous"
            } else {
                it.setImageResource(R.drawable.ic_status_normal)
                it.contentDescription = "Icon status normal"
            }
        }
        itemBinding.itemElement.setOnClickListener { v: View ->
            run {
                v.findNavController().navigate(MainFragmentDirections.actionShowDetail(item))
            }
        }
    }
}