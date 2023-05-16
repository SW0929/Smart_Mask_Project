package com.bliss.csc.smart_mask_sw9909

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bliss.csc.smart_mask_sw9909.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //tabLayout 객체 생성 및 탭 버튼 생성 코드
        val settingTap : TabLayout.Tab = binding.tabs.newTab()
        settingTap.text = "Setting"
        settingTap.setIcon(R.drawable.baseline_settings_24)
        binding.tabs.addTab(settingTap)

        val calendarTap : TabLayout.Tab = binding.tabs.newTab()
        calendarTap.text = "Calendar"
        calendarTap.setIcon(R.drawable.baseline_calendar_month_24)
        binding.tabs.addTab(calendarTap)

        val alarmTap : TabLayout.Tab = binding.tabs.newTab()
        alarmTap.text = "Alarm"
        alarmTap.setIcon(R.drawable.baseline_access_alarm_24)
        binding.tabs.addTab(alarmTap)

        val infoTap : TabLayout.Tab = binding.tabs.newTab()
        infoTap.setIcon(R.drawable.baseline_info_24)
        infoTap.text = "Information"
        binding.tabs.addTab(infoTap)

        //탭 버튼 이벤트 처리
//        binding.tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
//            //탭 버튼을 선택할 때 이벤트
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val transaction = supportFragmentManager.beginTransaction()
//
//                when(tab?.text){
//                    //TODO Fragment 만들고 여기 코드에 입력
//                    //example : "Tab1" -> transaction.replace(R.id.tabContent, OneFragment())
//                }
//                transaction.commit()
//            }
//            //선택된 탭 버튼을 다시 선택할 때 이벤트
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//            //다른 탭 버튼을 눌러 선택된 탭 버튼이 해제될 때 이벤트
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                TODO("Not yet implemented")
//            }
//
//        })

        //viewPager2 연동
//        TabLayoutMediator(tabLaout, viewPager){ tab, position ->
//            tab.text = "Tab${(position + 1)}"
//        }.attach()
    }
}

//class MypagerViewHolder(val binding: Item)