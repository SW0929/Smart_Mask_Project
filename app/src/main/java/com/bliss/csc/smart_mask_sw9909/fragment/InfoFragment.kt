package com.bliss.csc.smart_mask_sw9909.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bliss.csc.smart_mask_sw9909.databinding.InfoFragmentBinding
import com.bliss.csc.smart_mask_sw9909.fragment.chat.ChatBot

class InfoFragment: Fragment() {
    lateinit var binding: InfoFragmentBinding

    private lateinit var chatBot: ChatBot


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoFragmentBinding.inflate(inflater, container, false)


        // recyclerView 사용

        val apiKey = "*************************************" // todo 개인 gpt 키 사용
        val baseUrl = "https://api.openai.com/v1/chat/completions"
        val chatGptModel = "gpt-3.5-turbo" // 또는 원하는 ChatGPT 모델 지정

        chatBot = ChatBot(apiKey, baseUrl, chatGptModel)

        binding.sendButton.setOnClickListener {
            val message = binding.userInputEditText.text.toString().trim()

            if (message.isNotEmpty()) {
                binding.userInputEditText.text.clear()
                chatBot.sendMessage(message) { reply ->
                    activity?.runOnUiThread {
                        reply?.let {
                            binding.chatTextView.append("User: $message\n")
                            binding.chatTextView.append("ChatBot: $it\n\n")
                        }
                    }
                }
            }
        }

        return binding.root
    }


}