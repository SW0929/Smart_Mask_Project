package com.bliss.csc.smart_mask_sw9909.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bliss.csc.smart_mask_sw9909.R
import com.bliss.csc.smart_mask_sw9909.databinding.SettingFragmentBinding

class SettingFragment: Fragment() {
    lateinit var binding: SettingFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}

