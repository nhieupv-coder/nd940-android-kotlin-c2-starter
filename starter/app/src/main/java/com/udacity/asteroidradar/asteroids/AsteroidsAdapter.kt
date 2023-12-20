package com.udacity.asteroidradar.asteroids

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidsAdapter : ListAdapter<Asteroid,
        AsteroidItemViewHolder>(AsteroidCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidItemViewHolder {
        return from(parent)
    }

    private fun from(parent: ViewGroup): AsteroidItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AsteroidItemBinding.inflate(inflater, parent, false)
        return AsteroidItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class AsteroidCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}
