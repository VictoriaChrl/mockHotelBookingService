package com.example.mockhotelbookingservice.feature.hotel.hotel_info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.example.mockhotelbookingservice.feature.hotel.hotel_info.databinding.HotelPhotoItemBinding

class PhotoItemFragment : Fragment() {

    private var _binding: HotelPhotoItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = HotelPhotoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        private const val KEY_IMAGE = "image"

        fun newInstance(
            @DrawableRes drawableRes: Int
        ): PhotoItemFragment {
            val args = Bundle()

            args.putInt(KEY_IMAGE, drawableRes)
            return PhotoItemFragment().apply { arguments = args }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            photoSlide.setImageResource(requireArguments().getInt(KEY_IMAGE))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}