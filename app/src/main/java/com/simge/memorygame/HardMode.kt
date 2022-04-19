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
import kotlinx.android.synthetic.main.activity_hard_mode.*
import com.simge.memorygame.R.drawable.*
import com.simge.memorygame.databinding.ActivityEasyModeBinding
import com.simge.memorygame.databinding.ActivityHardModeBinding
import kotlinx.android.synthetic.main.activity_easy_mode.*

private const val TAG = "Hard Mode"
class HardMode : AppCompatActivity() {
    private lateinit var buttons2: List<ImageButton>
    private lateinit var cards2: List<MemoryCard2>
    private var indexOfSingleSelectedCard2: Int? = null
    lateinit var binding: ActivityHardModeBinding
    val context = this@HardMode
    var db = DataBaseHelper(context)

    private var timer = object : CountDownTimer(180000,1000){
        override fun onTick(i: Long) {
            textView6.setText((i / 1000).toString() + " secs.")
        }

        override fun onFinish() {
            val builder2 = AlertDialog.Builder(this@HardMode)
            builder2.setTitle("Game Over")
            builder2.setMessage("Time's Up! Your score: $puanEkle2, your moves: $moves2")
            val input = EditText(this@HardMode)
            input.setHint("Username")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder2.setView(input)
            builder2.setNegativeButton("OK"){_,_ ->

                var username = input.text.toString()
                if(username.isNotEmpty()){
                    println(username)
                    var score = puanEkle2.toString()
                    var time = "180 secs."
                    var user = UserModel(username,score, time)
                    db.insertData(user)

                }else{
                    Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                    val mainPage2 = Intent(this@HardMode, MainActivity::class.java)
                    startActivity(mainPage2)
                }
                val mainPage2 = Intent(this@HardMode, MainActivity::class.java)
                startActivity(mainPage2)

            }
            builder2.setPositiveButton("Go To Main Menu "){ _, _ ->
                val mainPage = Intent(this@HardMode, MainActivity::class.java)
                startActivity(mainPage)
            }
            val alertDialog2: AlertDialog = builder2.create()
            alertDialog2.setCancelable(false)
            alertDialog2.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_mode)
        val images2 = mutableListOf(
            beyazimsi,
            coolumsu,
            fazlapembe,
            kirmizikarisik,
            kirmizimsi,
            mavimsi,
            mavipembe,
            morkarisik,
            morumsu,
            moryesil,
            nudegerbera,
            pembekarisik,
            pembemsi,
            rose,
            sarikarisik,
            sarimsi,
            turkuaz,
            turuncumsu
        )

        images2.addAll(images2)
        images2.shuffle()

        buttons2 = listOf(
            imageButton17,
            imageButton18,
            imageButton19,
            imageButton20,
            imageButton21,
            imageButton22,
            imageButton23,
            imageButton24,
            imageButton25,
            imageButton26,
            imageButton27,
            imageButton28,
            imageButton29,
            imageButton30,
            imageButton31,
            imageButton32,
            imageButton33,
            imageButton34,
            imageButton35,
            imageButton36,
            imageButton37,
            imageButton38,
            imageButton39,
            imageButton40,
            imageButton41,
            imageButton42,
            imageButton43,
            imageButton44,
            imageButton45,
            imageButton46,
            imageButton47,
            imageButton48,
            imageButton49,
            imageButton50,
            imageButton51,
            imageButton52
        )

        cards2 = buttons2.indices.map { index ->
            MemoryCard2(images2[index])
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome")
        builder.setMessage(
            "In Hard Mode, you will have 3 minutes to find all pairs! " +
                    "When you feel ready to play, press OK"
        )
        builder.setPositiveButton("OK") { _, _ ->
            Toast.makeText(applicationContext, "Game started", Toast.LENGTH_SHORT).show()
            timer.start()

            buttons2.forEachIndexed { index, button2 ->
                button2.setOnClickListener {
                    Log.i(TAG, "button clicked!")
                    updateModels2(index)
                    updateViews2()
                    pairs2(index)

                }
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    var puanEkle2 = 0
    var moves2 = 0
    var pairs2 = 0

    private fun updateViews2() {
        cards2.forEachIndexed { index, card ->
            val button2 = buttons2[index]
            if(card.isMatched2){
                button2.alpha = 0.1f
                button2.isClickable = false


            }
            button2.setImageResource(if(card.isFaceUp2) card.identifier else R.drawable.ic_baseline_ac_unit_24)
        }
    }



    private fun updateModels2(position_: Int) {
        val card2 = cards2[position_]
        if(card2.isFaceUp2){
            Toast.makeText(this,"invalid move", Toast.LENGTH_LONG).show()
            return
        }
        if(indexOfSingleSelectedCard2 == null){
            restoreCards2()
            indexOfSingleSelectedCard2 = position_
        }else{
            checkForMatch2(indexOfSingleSelectedCard2!!,position_)
            indexOfSingleSelectedCard2 = null
        }
        card2.isFaceUp2 = !card2.isFaceUp2
    }




    private fun restoreCards2() {
        val moveTextView2: TextView = findViewById(R.id.textView9)
        for(card2 in cards2){
            if(!card2.isMatched2){
                card2.isFaceUp2 = false
            }
        }
        moves2 += 1
        moveTextView2.text="Moves: $moves2"
    }





    private fun pairs2(position: Int) {
        val pairTextView2: TextView = findViewById(R.id.textView2)
        val card2 = cards2[position]
        if(card2.isMatched2){
            pairs2 += 1
            pairTextView2.text= "Pairs: $pairs2/18"
            if(pairs2 == 18){
                timer.cancel()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Congrats!!")
                builder.setMessage("Game over. You found all 18 pairs with $moves2 moves. Your score is $puanEkle2.")
                val input = EditText(this@HardMode)
                input.setHint("Username")
                input.inputType = InputType.TYPE_CLASS_TEXT
                builder.setView(input)

                builder.setPositiveButton("Go to Main Menu"){ _, _ ->
                    var username = input.text.toString()
                    if(username.isNotEmpty()){
                        println(username)
                        var score = puanEkle2.toString()
                        var time = textView6.text.toString()
                        var user = UserModel(username,score, time)
                        db.insertData(user)

                    }else{
                        Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                        val mainPage = Intent(this@HardMode, MainActivity::class.java)
                        startActivity(mainPage)
                    }
                    Toast.makeText(applicationContext, "Going to Main Menu", Toast.LENGTH_SHORT).show()
                    val mainPage = Intent(this@HardMode, MainActivity::class.java)
                    startActivity(mainPage)
                }
                builder.setNeutralButton("Play Again"){ _, _ ->
                    var username = input.text.toString()
                    if(username.isNotEmpty()){
                        println(username)
                        //database

                    }else{
                        Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                        val mainPage = Intent(this@HardMode, MainActivity::class.java)
                        startActivity(mainPage)
                    }
                    Toast.makeText(applicationContext, "Clicked Play Again", Toast.LENGTH_SHORT).show()
                    val hardMode = Intent(this@HardMode, HardMode::class.java)
                    startActivity(hardMode)

                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

        }
    }




    private fun checkForMatch2(position1: Int, position2: Int) {
        val puanTextView2: TextView = findViewById(R.id.textView8)
        if(cards2[position1].identifier == cards2[position2].identifier){
            Toast.makeText(this,"Match Found!", Toast.LENGTH_SHORT).show()
            cards2[position1].isMatched2 = true
            cards2[position2].isMatched2 = true
            puanEkle2 += 10
            puanTextView2.text= "Point: $puanEkle2"
        }
        else{
            puanEkle2 -= 2
            puanTextView2.text= "Point: $puanEkle2"

    }
}
}