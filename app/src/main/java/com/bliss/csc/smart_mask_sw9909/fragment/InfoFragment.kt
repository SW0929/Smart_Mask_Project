package com.bliss.csc.smart_mask_sw9909.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val apiKey = "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" // todo 개인 gpt 키 사용
        val baseUrl = "https://api.openai.com/v1/chat/completions"
        val chatGptModel = "gpt-3.5-turbo" // 또는 원하는 ChatGPT 모델 지정

        chatBot = ChatBot(apiKey, baseUrl, chatGptModel)

        binding.sendButton.setOnClickListener {

            val message = binding.userInputEditText.text.toString().trim()

            //User 가 질문을 입력하면 chatBot에서 답을 출력
            if (message.isNotEmpty()) {
                binding.userInputEditText.text.clear()
                //왜 키보드가 안내려갈까
                chatBot.sendMessage(message) { reply ->
                    activity?.runOnUiThread {
                        reply?.let {
                            val userText = "User: $message\n"
                            val chatBotText = "ChatBot: $it\n\n"

                            val spannableString = SpannableString(userText + chatBotText)

                            // User 텍스트에 다른 색상 적용
                            spannableString.setSpan(
                                ForegroundColorSpan(Color.BLACK),
                                0, // 시작 인덱스
                                userText.length, // 끝 인덱스
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )

                            // ChatBot 텍스트에 다른 색상 적용
                            spannableString.setSpan(
                                ForegroundColorSpan(Color.rgb(100, 149, 237)),
                                userText.length, // 시작 인덱스
                                spannableString.length, // 끝 인덱스
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                            )

                            binding.chatTextView.append(spannableString)

                        }
                    }
                }
            }

//            if (message.isNotEmpty()) {
//                binding.userInputEditText.text.clear()
//                chatBot.sendMessage(message) { reply ->
//                    activity?.runOnUiThread {
//                        reply?.let {
//
//                            binding.chatTextView.append("User: $message\n")
//                            binding.chatTextView.append("ChatBot: $it\n\n")
//                        }
//                    }
//                }
//            }
        }

        return binding.root
    }


}