package com.bliss.csc.smart_mask_sw9909.fragment.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.bliss.csc.smart_mask_sw9909.databinding.ItemMainBinding

class MyAdapter(val datas: MutableList<String>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    //항목 개수를 판단하려고 자동으로 호출
    override fun getItemCount(): Int = datas.size


    //항목의 뷰를 가지는 뷰 홀더를 준비하려고 자동으로 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    //뷰 홀더의 뷰에 데이터를 출력하려고 자동으로 호출
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding

        //뷰에 데이터 출력
        binding.itemData.text = datas[position]
        //뷰에 이벤트 추가
        binding.itemRoot.setOnClickListener {

        }
    }

}
