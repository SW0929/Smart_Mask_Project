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
import com.bliss.csc.smart_mask_sw9909.Database.DatabaseHelper
import com.bliss.csc.smart_mask_sw9909.databinding.AlarmFragmentBinding
import com.bliss.csc.smart_mask_sw9909.fragment.alarm.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmFragment: Fragment(){
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var binding: AlarmFragmentBinding

    lateinit var settingFragment: SettingFragment

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        settingFragment = childFragment as SettingFragment
    }

    var alarmTimePicker: TimePicker? = null
    var pendingIntent: PendingIntent? = null
    var alarmManager: AlarmManager? = null

    var pauseTime = 0L

    //mNow, mDate, mFormat 은 현재 시간을 구하기 위한 변수

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AlarmFragmentBinding.inflate(inflater, container, false)
        val context = requireContext()
        databaseHelper = DatabaseHelper(context)
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager

        var currentDate: String? = null

        settingFragment = SettingFragment()



        //수면 시작 버튼을 누르면 stopWatch 처럼 시간 증가(총 수면 시간을 구할 수 있음)
        binding.sleepStartButton.setOnClickListener {
            //아두이노 센서 및 수면 시작
            settingFragment.bluetoothStart()


            currentDate = getCurrentDate()
            val currentTime_start = getCurrentTime()
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()

            databaseHelper.insertData_user(currentDate, currentTime_start) //수면시작버튼 클릭 시
            Toast.makeText(this.context, "TODAY($currentDate) 수면기록이 시작되었습니다.", Toast.LENGTH_SHORT).show()
            //--> 센서 작동ON Function
        }

        //수면 종료
        binding.sleepEndButton.setOnClickListener {
            val currentTime_finish = getCurrentTime()
            val selectdate = currentDate
            println("수면 시작 시간: $selectdate")
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            //binding.sleepEndTimeTextView.text = getTime() -> 현재 시간

            val recordtime = databaseHelper.getSleepstart(selectdate.toString()) //오늘날짜의 수면시작시간을 GET(종료버튼 클릭 시 필요한 변수)
            val recordedTimeToSeconds = databaseHelper.convertToSeconds(recordtime.toString()) //기록된 sleepstart를 초로 환산한 값
            val currenttimetoSeconds = databaseHelper.convertToSeconds(currentTime_finish) //수면종료시간(sleepfinish)을 초로 환산한 값
            val converttime = databaseHelper.convertToTime(currenttimetoSeconds-recordedTimeToSeconds) //update (총수면시간)
            //val averagetemp = databaseHelper.gettempAverage(currentDate) //기록된 온도값들의 평균값 계산
            //val countnose :Int= databaseHelper.getNoseCount(currentDate) //기록된 코골이값들을 COUNT

            println("수면 시작 시간: $recordtime")
            println("수면시작시간의 초환산 값: $recordedTimeToSeconds")
            println("수면 종료 시간: $currentTime_finish")
            println("수면종료시간의 초환산 값: $currenttimetoSeconds")
            println("총수면시간: $converttime")
            //println("평균온도값: $averagetemp")
            //println("코골이 횟수 count: $countnose")

            databaseHelper.updateSleepfinish(selectdate.toString(), recordtime.toString(), currentTime_finish) //수면종료버튼 클릭 시
            Toast.makeText(this.context, "TODAY($currentDate) 수면기록이 종료되었습니다.", Toast.LENGTH_SHORT).show()
            //--> 센서 작동OFF Function

            binding.chronometer.stop()
            //아두이노 센서 및 수면 종료
            settingFragment.outputStream?.write("e\n".toByteArray())
        }


        //초기화
        binding.sleepResetButton.setOnClickListener {
            val currentDate = getCurrentDate()

            pauseTime = 0L
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()

            databaseHelper.deleteData(currentDate) //User, Sound, Temp table 삭제
            Toast.makeText(this.context, "TODAY($currentDate) 날짜의 수면기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            //--> 센서 작동OFF Function
        }

        binding.alarmStartButton.setOnClickListener {
            setAlarm()

            val currentDate = getCurrentDate()

            val alarmTime = String.format("%02d:%02d", binding.timePicker.hour, binding.timePicker.minute)
            println("설정한 알람 시간: $alarmTime")
            databaseHelper.updateSleepfinish(currentDate, alarmTime) //User table col(알람시간) UPDATE
        }


        return binding.root
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

    //현재 날짜 구하는 Function
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }
    //현재 시간 구하는 Function
    fun getCurrentTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val currentTime = Date()
        return timeFormat.format(currentTime)
    }
}