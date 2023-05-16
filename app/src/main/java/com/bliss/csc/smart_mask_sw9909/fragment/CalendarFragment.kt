package com.bliss.csc.smart_mask_sw9909.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bliss.csc.smart_mask_sw9909.R
import com.bliss.csc.smart_mask_sw9909.databinding.CalendarFragmentBinding

class CalendarFragment: Fragment() {
    lateinit var binding: CalendarFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}