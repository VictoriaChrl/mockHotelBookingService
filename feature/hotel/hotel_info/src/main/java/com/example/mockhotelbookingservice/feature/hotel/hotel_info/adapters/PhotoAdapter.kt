package com.example.mockhotelbookingservice.feature.hotel.hotel_info.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.databinding.HotelPhotoItemBinding

class PhotoAdapter(private val imageUrls: List<String>,
                   private val context: Context
) : RecyclerView.Adapter<PhotoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(
            HotelPhotoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val image = imageUrls[position]
        Glide.with(context)
            .load(image)
            .into(holder.binding.photoSlide)
    }

    override fun getItemCount(): Int = imageUrls.size
}
