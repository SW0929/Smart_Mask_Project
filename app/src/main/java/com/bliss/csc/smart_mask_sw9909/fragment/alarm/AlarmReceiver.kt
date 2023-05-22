package com.bliss.csc.smart_mask_sw9909.fragment.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Vibrator
import android.widget.Toast
import com.bliss.csc.smart_mask_sw9909.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val vibrator = context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)

        // 알람이 울릴 때 수행할 작업을 여기에 추가합니다.
        Toast.makeText(context, "알람이 울립니다!", Toast.LENGTH_SHORT).show()

        // 예를 들어, 알람이 울릴 때 사운드를 재생할 수도 있습니다.
        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound)
        mediaPlayer.start()
    }

}