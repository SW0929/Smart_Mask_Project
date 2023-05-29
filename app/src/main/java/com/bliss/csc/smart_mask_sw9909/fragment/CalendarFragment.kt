package com.bliss.csc.smart_mask_sw9909.fragment

import android.database.Cursor
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.bliss.csc.smart_mask_sw9909.Database.DatabaseHelper
import com.bliss.csc.smart_mask_sw9909.R
import com.bliss.csc.smart_mask_sw9909.databinding.CalendarFragmentBinding

class CalendarFragment: Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var binding: CalendarFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarFragmentBinding.inflate(inflater, container, false)

        val context = requireContext()
        databaseHelper = DatabaseHelper(context)

        //Todo default 오늘 날자 기록 표시 (필요 시)

//사용자가 원하는 날짜 선택하면 기록 조회
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            binding.dateTextView.visibility = View.VISIBLE
            binding.dateTextView.text = "$year-${String.format("%02d", month+1)}-$dayOfMonth"
        }
        binding.recordQueryButton.setOnClickListener {
            val datetext = binding.dateTextView.text

            val cursor: Cursor? = databaseHelper.getCalendarRecord(datetext.toString())

            if (cursor != null && cursor.moveToFirst()) {
                val column1Value = cursor.getString(cursor.getColumnIndexOrThrow("UserPK"))
                val column2Value = cursor.getString(cursor.getColumnIndexOrThrow("Sleepstart"))
                val column3Value = cursor.getString(cursor.getColumnIndexOrThrow("Sleepfinish"))
                val column4Value = cursor.getString(cursor.getColumnIndexOrThrow("Sleeptime"))
                val column5Value = cursor.getString(cursor.getColumnIndexOrThrow("Averagetemp"))
                val column6Value = cursor.getString(cursor.getColumnIndexOrThrow("Nosecount"))
                println("변환된 시간: $column1Value, $column2Value, $column3Value, $column4Value, $column5Value, $column6Value")
                binding.startSleepValueTextView.text = column2Value
                binding.endSleepValueTextView.text = column3Value
                binding.totalSleepValueTextView.text = column4Value
                binding.temValueTextView.text = column5Value + "°C"
                binding.snoreValueTextView.text = column6Value + "회"
            } else {
                println("not found")
                binding.startSleepValueTextView.text = "Not Exist"
                binding.endSleepValueTextView.text = "Not Exist"
                binding.totalSleepValueTextView.text = "Not Exist"
                binding.temValueTextView.text = "Not Exist"
                binding.snoreValueTextView.text = "Not Exist"
                Toast.makeText(this.context, "$datetext 날짜의 수면기록이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            cursor?.close()
        }

        return binding.root
    }
}