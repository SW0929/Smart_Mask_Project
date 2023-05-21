package com.bliss.csc.smart_mask_sw9909

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bliss.csc.smart_mask_sw9909.fragment.AlarmFragment
import com.bliss.csc.smart_mask_sw9909.fragment.CalendarFragment
import com.bliss.csc.smart_mask_sw9909.fragment.InfoFragment
import com.bliss.csc.smart_mask_sw9909.fragment.SettingFragment

class FragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val fragments: List<Fragment> =
        listOf(SettingFragment(), CalendarFragment(), AlarmFragment(), InfoFragment())

    init {
        Log.d("fragment", "fragments.size : ${fragments.size}")
    }
    override fun getItemCount(): Int = fragments.size


    //메뉴의 순서대로 보여줄 레이아웃인 Fragment를 반환
    override fun createFragment(position: Int): Fragment = when(position){
        0 -> SettingFragment()
        1 -> CalendarFragment()
        2 -> AlarmFragment()
        else -> InfoFragment()
    }

}