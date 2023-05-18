package com.bliss.csc.smart_mask_sw9909.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bliss.csc.smart_mask_sw9909.R
import com.bliss.csc.smart_mask_sw9909.databinding.ItemMainBinding
import com.bliss.csc.smart_mask_sw9909.databinding.SettingFragmentBinding
import com.bliss.csc.smart_mask_sw9909.fragment.recyclerview.MyAdapter

class SettingFragment: Fragment() {

    lateinit var binding: SettingFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingFragmentBinding.inflate(inflater, container, false)

        //bluetooth On/Off 동작
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                binding.onRadioButton.id -> {
                    //TODO bluetooth 연결 코드 작성
                    Log.d("on", "on is selected")
                }
                binding.offRadioButton.id -> {
                    //TODO bluetooth 해제 코드 작성
                    Log.d("off", "off is selected")
                }
            }
        }

        //연결 가능한 bluetooth device listView
        binding.spdCheckBox.setOnClickListener {
            if (binding.spdCheckBox.isChecked){
                binding.devicesRecyclerView.visibility = View.VISIBLE
            }else{
                binding.devicesRecyclerView.visibility = View.INVISIBLE
            }
        }

        //recyclerView 출력
        val datas = mutableListOf<String>()
        for(i in 1..20){
            datas.add("Item $i")
        }
        val adapter = MyAdapter(datas)
        val layoutManager = LinearLayoutManager(activity)

        binding.devicesRecyclerView.layoutManager = layoutManager
        binding.devicesRecyclerView.adapter = adapter

        return binding.root
    }
}

