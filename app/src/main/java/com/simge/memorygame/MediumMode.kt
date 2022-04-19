package com.simge.memorygame


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
//import kotlinx.android.synthetic.main.activity_hard_mode.*
import kotlinx.android.synthetic.main.activity_medium_mode.*
import com.simge.memorygame.R.drawable.*
//import com.simge.memorygame.databinding.ActivityEasyModeBinding
//import com.simge.memorygame.databinding.ActivityHardModeBinding
import com.simge.memorygame.databinding.ActivityMediumModeBinding
import kotlinx.android.synthetic.main.activity_easy_mode.*

private const val TAG = "Medium Mode"
class MediumMode : AppCompatActivity() {
    private lateinit var buttons3: List<ImageButton>
    private lateinit var cards3: List<MemoryCard3>
    private var indexOfSingleSelectedCard3: Int? = null
    lateinit var binding: ActivityMediumModeBinding
    val context = this@MediumMode
    var db = DataBaseHelper(context)

    private var timer = object : CountDownTimer(60000,1000){
        override fun onTick(i: Long) {
            textView6.setText((i / 1000).toString() + " secs.")
        }

        override fun onFinish() {
            val builder3 = AlertDialog.Builder(this@MediumMode)
            builder3.setTitle("Game Over")
            builder3.setMessage("Time's Up! Your score: $puanEkle3, your moves: $moves3")
            val input = EditText(this@MediumMode)
            input.setHint("Username")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder3.setView(input)
            builder3.setNegativeButton("OK"){_,_ ->

                var username = input.text.toString()
                if(username.isNotEmpty()){
                    println(username)
                    var score = puanEkle3.toString()
                    var time = "60 secs."
                    var user = UserModel(username,score, time)
                    db.insertData(user)

                }else{
                    Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                    val mainPage3 = Intent(this@MediumMode, MainActivity::class.java)
                    startActivity(mainPage3)
                }
                val mainPage3 = Intent(this@MediumMode, MainActivity::class.java)
                startActivity(mainPage3)

            }
            builder3.setPositiveButton("Go To Main Menu "){ _, _ ->
                val mainPage = Intent(this@MediumMode, MainActivity::class.java)
                startActivity(mainPage)
            }
            val alertDialog3: AlertDialog = builder3.create()
            alertDialog3.setCancelable(false)
            alertDialog3.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_mode)
        val images3 = mutableListOf(
            cry,
            dusunce,
            endise,
            gozluklu,
            kalpgozlu,
            kizgin,
            melek,
            mindblown,
            saskin,
            seytan,
            sleepy,
            smile,


        )

        images3.addAll(images3)
        images3.shuffle()

        buttons3 = listOf(
            imageButton52,
            imageButton18,
            imageButton19,
            imageButton20,
            imageButton26,
            imageButton32,
            imageButton42,
            imageButton36,
            imageButton23,
            imageButton31,
            imageButton25,
            imageButton37,
            imageButton24,
            imageButton30,
            imageButton33,
            imageButton38,
            imageButton21,
            imageButton29,
            imageButton34,
            imageButton39,
            imageButton27,
            imageButton28,
            imageButton35,
            imageButton40,

        )

        cards3 = buttons3.indices.map { index ->
            MemoryCard3(images3[index])
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome")
        builder.setMessage(
            "In Medium Mode, you will have 60 seconds to find all pairs! " +
                    "When you feel ready to play, press OK"
        )
        builder.setPositiveButton("OK") { _, _ ->
            Toast.makeText(applicationContext, "Game started", Toast.LENGTH_SHORT).show()
            timer.start()

            buttons3.forEachIndexed { index, button3 ->
                button3.setOnClickListener {
                    Log.i(TAG, "button clicked!")
                    updateModels3(index)
                    updateViews3()
                    pairs3(index)

                }
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    var puanEkle3 = 0
    var moves3 = 0
    var pairs3 = 0

    private fun updateViews3() {
        val moveTextView3: TextView = findViewById(R.id.textView9)
        cards3.forEachIndexed { index, card ->
            val button3 = buttons3[index]
            if(card.isMatched3){
                button3.alpha = 0.1f
                button3.isClickable = false


            }
            button3.setImageResource(if(card.isFaceUp3) card.identifier else R.drawable.ic_baseline_ac_unit_24)
        }
        moves3 += 1
        moveTextView3.text="Moves: $moves3"
    }



    private fun updateModels3(position_: Int) {
        val card3 = cards3[position_]
        if(card3.isFaceUp3){
            Toast.makeText(this,"invalid move", Toast.LENGTH_LONG).show()
            return
        }
        if(indexOfSingleSelectedCard3 == null){
            restoreCards3()
            indexOfSingleSelectedCard3 = position_
        }else{
            checkForMatch3(indexOfSingleSelectedCard3!!,position_)
            indexOfSingleSelectedCard3 = null
        }
        card3.isFaceUp3 = !card3.isFaceUp3
    }




    private fun restoreCards3() {
        for(card3 in cards3){
            if(!card3.isMatched3){
                card3.isFaceUp3 = false
            }
        }

    }





    private fun pairs3(position: Int) {
        val pairTextView3: TextView = findViewById(R.id.textView2)
        val card3 = cards3[position]
        if(card3.isMatched3){
            pairs3 += 1
            pairTextView3.text= "Pairs: $pairs3/12"
            if(pairs3 == 12){
                timer.cancel()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Congrats!!")
                builder.setMessage("Game over. You found all 12 pairs with $moves3 moves. Your score is $puanEkle3.")
                val input = EditText(this@MediumMode)
                input.setHint("Username")
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("Go to Main Menu"){ _, _ ->
                    var username = input.text.toString()
                    if(username.isNotEmpty()){
                        println(username)
                        var score = puanEkle3.toString()
                        var time = textView6.text.toString()
                        var user = UserModel(username,score, time)
                        db.insertData(user)

                    }else{
                        Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                        val mainPage = Intent(this@MediumMode, MainActivity::class.java)
                        startActivity(mainPage)
                    }
                    Toast.makeText(applicationContext, "Going to Main Menu", Toast.LENGTH_SHORT).show()
                    val mainPage = Intent(this@MediumMode, MainActivity::class.java)
                    startActivity(mainPage)
                }
                builder.setNeutralButton("Play Again"){ _, _ ->
                    var username = input.text.toString()
                    if(username.isNotEmpty()){
                        println(username)
                        //database

                    }else{
                        Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                        val mainPage = Intent(this@MediumMode, MainActivity::class.java)
                        startActivity(mainPage)
                    }
                    Toast.makeText(applicationContext, "Clicked Play Again", Toast.LENGTH_SHORT).show()
                    val mediumMode = Intent(this@MediumMode, MediumMode::class.java)
                    startActivity(mediumMode)

                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

        }
    }




    private fun checkForMatch3(position1: Int, position2: Int) {
        val puanTextView3: TextView = findViewById(R.id.textView8)
        if(cards3[position1].identifier == cards3[position2].identifier){
            Toast.makeText(this,"Match Found!", Toast.LENGTH_SHORT).show()
            cards3[position1].isMatched3 = true
            cards3[position2].isMatched3 = true
            puanEkle3 += 10
            puanTextView3.text= "Point: $puanEkle3"
        }
        else{
            puanEkle3 -= 2
            puanTextView3.text= "Point: $puanEkle3"

        }
    }
}