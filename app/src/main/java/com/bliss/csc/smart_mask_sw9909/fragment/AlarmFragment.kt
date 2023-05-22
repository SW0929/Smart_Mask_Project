package com.bliss.csc.smart_mask_sw9909.fragment

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bliss.csc.smart_mask_sw9909.databinding.AlarmFragmentBinding
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment: Fragment() {
    lateinit var binding: AlarmFragmentBinding

    var pauseTime = 0L

    //mNow, mDate, mFormat 은 현재 시간을 구하기 위한 변수수
   var mNow: Long = 0
    var mDate: Date? = null
    var mFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlarmFragmentBinding.inflate(inflater, container, false)



        //수면 시작 버튼을 누르면 stopWatch 처럼 시간 증가(총 수면 시간을 구할 수 있음)
        binding.sleepStartButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()
            binding.sleepStartTimeTextView.text = getTime()
        }

        //수면 종료
        binding.sleepEndButton.setOnClickListener {

            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.sleepEndTimeTextView.text = getTime()
            binding.chronometer.stop()
        }


        //초기화
        binding.sleepResetButton.setOnClickListener {

            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
        }



        return binding.root
    }
    //현재 시간
    private fun getTime(): String? {
        mNow = System.currentTimeMillis()
        mDate = Date(mNow)
        return mFormat.format(mDate)
    }

}