package com.example.mockhotelbookingservice.feature.hotel.room_booking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mockhotelbookingservice.feature.hotel.room_booking.databinding.ItemGuestBinding
import com.example.mockhotelbookingservice.feature.hotel.room_booking.util.OnEditTextChangedListener

class NewGuestAdapter(
    initGuestList: List<Guest>,
    private val editTextChangedListener: OnEditTextChangedListener
) :
    RecyclerView.Adapter<NewGuestHolder>() {

    private val guestMutableList =
        mutableListOf<Guest>()


    init {
        guestMutableList.addAll(initGuestList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewGuestHolder {
        return NewGuestHolder(
            ItemGuestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), editTextChangedListener
        )
    }

    override fun onBindViewHolder(holder: NewGuestHolder, position: Int) {
        holder.bind(guestMutableList[position])
    }

    override fun getItemCount(): Int {
        return guestMutableList.size
    }

    fun update(newList: List<Guest>) {
        val diffCallback = GuestDiffCallback(guestMutableList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        guestMutableList.clear()
        guestMutableList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

}

class GuestDiffCallback(
    private val oldList: List<Guest>,
    private val newList: List<Guest>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

}



