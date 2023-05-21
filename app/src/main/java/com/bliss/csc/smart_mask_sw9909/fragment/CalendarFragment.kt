package com.bliss.csc.smart_mask_sw9909.fragment

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
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

        //Todo default 오늘 날자 기록 표시 (필요 시)

        //사용자가 원하는 날짜 선택하면 기록 조회
       binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            binding.dateTextView.visibility = View.VISIBLE
            binding.dateTextView.text = "$year/${(month+1)}/$dayOfMonth"
        }




        return binding.root
    }
}