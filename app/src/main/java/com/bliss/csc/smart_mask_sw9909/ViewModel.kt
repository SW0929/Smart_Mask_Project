package com.bliss.csc.smart_mask_sw9909

import androidx.lifecycle.MutableLiveData

class ViewModel {
    var inputText : MutableLiveData<String> = MutableLiveData()

    fun getData(): MutableLiveData<String> = inputText

    fun updateText(input: String) {
        inputText.value = input
    }
}