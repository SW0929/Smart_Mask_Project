package com.bliss.csc.smart_mask_sw9909.fragment.chat

import android.util.Log
import android.widget.Toast
import com.bliss.csc.smart_mask_sw9909.fragment.InfoFragment
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.coroutineContext


class ChatBot(private val apiKey: String, private val baseUrl: String, private val model: String) {

    fun sendMessage(message: String, callback: (String?) -> Unit) {
        val requestData = """
            {
                "messages": [
                    {"role": "system", "content": "You are a chatbot."},
                    {"role": "user", "content": "$message"}
                ],
                "model": "$model"
            }
        """.trimIndent()

        val client = OkHttpClient()

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), requestData)

        val request = Request.Builder()
            .url(baseUrl)
            .header("Authorization", "Bearer $apiKey")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val reply = processResponse(responseBody)
                callback(reply)
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }
        })
    }

    private fun processResponse(responseBody: String?): String? {
        if (responseBody.isNullOrEmpty()) return null

        val jsonObject = JSONObject(responseBody)
        val choicesJsonArray = jsonObject.optJSONArray("choices")
        if (choicesJsonArray == null || choicesJsonArray.length() == 0) {
            Log.d("error", "error")
            return null
        }

        val choiceJson = choicesJsonArray.getJSONObject(0)
        val messageJson = choiceJson.getJSONObject("message")
        return messageJson.optString("content")

    }
}