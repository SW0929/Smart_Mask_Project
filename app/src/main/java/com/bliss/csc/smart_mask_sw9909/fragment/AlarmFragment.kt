package com.bliss.csc.smart_mask_sw9909.fragment


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bliss.csc.smart_mask_sw9909.databinding.AlarmFragmentBinding
import com.bliss.csc.smart_mask_sw9909.fragment.alarm.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment: Fragment(){

    lateinit var binding: AlarmFragmentBinding


    var alarmTimePicker: TimePicker? = null
    var pendingIntent: PendingIntent? = null
    var alarmManager: AlarmManager? = null

    var pauseTime = 0L

    //mNow, mDate, mFormat 은 현재 시간을 구하기 위한 변수
   var mNow: Long = 0
    var mDate: Date? = null
    var mFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlarmFragmentBinding.inflate(inflater, container, false)

        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager



        //수면 시작 버튼을 누르면 stopWatch 처럼 시간 증가(총 수면 시간을 구할 수 있음)
        binding.sleepStartButton.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()

        }

        //수면 종료
        binding.sleepEndButton.setOnClickListener {

            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            //binding.sleepEndTimeTextView.text = getTime() -> 현재 시간
            binding.chronometer.stop()
        }


        //초기화
        binding.sleepResetButton.setOnClickListener {

            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
        }

        binding.alarmStartButton.setOnClickListener {
            setAlarm()
        }


        return binding.root
    }


    //현재 시간
    private fun getTime(): String? {
        mNow = System.currentTimeMillis()
        mDate = Date(mNow)
        return mFormat.format(mDate)
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
        calendar.set(Calendar.MINUTE, binding.timePicker.minute)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this.context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this.context, "알람이 설정되었습니다.", Toast.LENGTH_SHORT).show()
    }

}