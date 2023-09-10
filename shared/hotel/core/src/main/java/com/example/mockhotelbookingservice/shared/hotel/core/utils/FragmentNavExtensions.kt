package com.example.mockhotelbookingservice.shared.hotel.core.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

fun Fragment.navigate(actionId: Int, hostId: Int? = null, data: String? = null) {
	val navController = if (hostId == null) {
		findNavController()
	} else {
		Navigation.findNavController(requireActivity(), hostId)
	}

	val bundle = Bundle().apply {
		data?.let {
			putString("navigation_data", it)
		}
	}

	navController.navigate(actionId, bundle)
}

fun Fragment.navigateBack(){
	findNavController().popBackStack()
}

val Fragment.navigationData: String?
	get() = arguments?.getString("navigation_data", "").let { if (it == "") null else it }
