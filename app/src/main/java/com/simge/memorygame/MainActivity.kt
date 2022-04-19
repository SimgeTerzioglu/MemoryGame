package com.simge.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonClick=findViewById<Button>(R.id.button)
        val buttonClick1 =findViewById<Button>(R.id.button4)
        val buttonClick2 =findViewById<Button>(R.id.button3)
        val buttonClick3 =findViewById<Button>(R.id.button5)



        buttonClick.setOnClickListener{
            val intent = Intent(this, EasyMode::class.java)
            startActivity(intent)

        }
        buttonClick1.setOnClickListener{
            val intent1 = Intent(this, HardMode::class.java)
            startActivity(intent1)
        }
        buttonClick2.setOnClickListener{
            val intent2 = Intent(this, scores::class.java)
            startActivity(intent2)
        }
        buttonClick3.setOnClickListener{
            val intent3 = Intent(this, MediumMode::class.java)
            startActivity(intent3)
        }

    }
}