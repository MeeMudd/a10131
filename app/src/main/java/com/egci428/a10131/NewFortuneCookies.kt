package com.egci428.a10131

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egci428.a10131.Model.Wish
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class NewFortuneCookies : AppCompatActivity() {

    val num = Random.nextInt(0, 9).toString()
    val jsonURL =
        "https://egci428-d78f6-default-rtdb.firebaseio.com/fortunecookies/" + num + ".json"

    private val client = OkHttpClient()
    var message = "null"
    var status = "null"
    var date  = "null"
    lateinit var backBtn:ImageButton
    lateinit var closeCookie:ImageView
    lateinit var resultText:TextView
    lateinit var dateText:TextView
    lateinit var onPicText:TextView
    lateinit var makeAWishBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newfortunecookie)

        backBtn = findViewById(R.id.backBtn)
        resultText = findViewById(R.id.resultText)
        dateText = findViewById(R.id.dateText)
        makeAWishBtn = findViewById(R.id.makeAWishBtn)
        onPicText = findViewById(R.id.onPicText)
        closeCookie = findViewById(R.id.cookieView)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val current = LocalDateTime.now().format(formatter)


        closeCookie.setImageResource(R.drawable.closed_cookie)

        dateText.setText("Date:     ")
        resultText.setText("Result: No Result ")


        fun fetchJson(){
            val request = Request.Builder()
                .url(jsonURL)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        for ((name, value) in response.headers) {
                            println("$name: $value")
                        }
                        val body = response.body!!.string()
                        if(body == null) return@use
                        val gson = GsonBuilder().create()
                        val Wish = gson.fromJson(body,
                            Wish::class.java)

                        message=Wish.message
                        status=Wish.status
                        date=dateText.text.toString()
                        onPicText.text = Wish.message
                        resultText.text = "Result: "+Wish.message


                    }
                }
            })
        }
        makeAWishBtn.setOnClickListener {
            if(makeAWishBtn.text.equals("Make a Wish")){
                Toast.makeText(this,"Waiting",Toast.LENGTH_SHORT).show()
                fetchJson()
                closeCookie.setImageResource(R.drawable.opened_cookie)
                dateText.setText("Date: " + current.toString())
                makeAWishBtn.setText("Save")

            } else if (makeAWishBtn.text.equals("Save")){


                var intentWish = Intent(this, MainActivity::class.java)
                intentWish.putExtra("messageIntent",message)
                intentWish.putExtra("statusIntent",status)
                intentWish.putExtra("dateIntent",date)
                setResult(RESULT_OK,intentWish)
                finish()
            }
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //setResult(RESULT_OK,intent)
            finish()
        }
    }
}