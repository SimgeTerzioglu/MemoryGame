package com.simge.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.widget.ImageButton
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_easy_mode.*
import com.simge.memorygame.R.drawable.*
import com.simge.memorygame.databinding.ActivityEasyModeBinding


private const val TAG = "Easy Mode"

class EasyMode : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    lateinit var binding: ActivityEasyModeBinding
    val context = this@EasyMode
    var db = DataBaseHelper(context)

    private var timer = object : CountDownTimer(45000,1000){
        override fun onTick(i: Long) {
            textView5.setText((i / 1000).toString() + " secs.")
        }

        override fun onFinish() {

            val builder2 = AlertDialog.Builder(this@EasyMode)
                    builder2.setTitle("Game Over")
                    builder2.setMessage("Time's Up! Your score: $puanEkle, your moves: $moves")
                    //
                    val input = EditText(this@EasyMode)
                    input.setHint("Username")
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    builder2.setView(input)
                    builder2.setNegativeButton("OK"){_,_ ->

                        var username = input.text.toString()
                        if(username.isNotEmpty()){
                        println(username)
                            var score = puanEkle.toString()
                            var time = "45 secs."
                            var user = UserModel(username,score, time)
                            db.insertData(user)

                        }else{
                            Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                            val mainPage = Intent(this@EasyMode, MainActivity::class.java)
                            startActivity(mainPage)
                        }
                        val mainPage = Intent(this@EasyMode, MainActivity::class.java)
                        startActivity(mainPage)
                    }

                    builder2.setPositiveButton("Go To Main Menu "){ _, _ ->
                        val mainPage = Intent(this@EasyMode, MainActivity::class.java)
                        startActivity(mainPage)
                }
                    val alertDialog2: AlertDialog = builder2.create()
                    alertDialog2.setCancelable(false)
                    alertDialog2.show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEasyModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_easy_mode)


        val images = mutableListOf(portakal,ananas,avukado,blackberry,cilek,raspberry,nar,kiraz )
        images.addAll(images)
        images.shuffle()
        buttons = listOf(imageButton, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8,
        imageButton9, imageButton10, imageButton11, imageButton12, imageButton13, imageButton14, imageButton15, imageButton16)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome")
        builder.setMessage("In Easy Mode, you will have 45 seconds to find all pairs! " +
                "When you feel ready to play, press OK")
        builder.setPositiveButton("OK"){ _, _ ->
            Toast.makeText(applicationContext, "Game started", Toast.LENGTH_SHORT).show()
            timer.start()

            buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    Log.i(TAG,  "button clicked!")
                    updateModels(index)
                    updateViews()
                    pair(index)

                }
            }

        }


        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

        }

        private fun pair(position: Int) {
        val pairTextView: TextView = findViewById(R.id.textView4)
        val card = cards[position]
        if(card.isMatched){
            pairs += 1
            pairTextView.text= "Pairs: $pairs/8"
        }
        if(pairs == 8){
            timer.cancel()

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Congrats!!")
                builder.setMessage("Game over. You found all 8 pairs with $moves moves. Your score is $puanEkle.")
            val input = EditText(this@EasyMode)
            input.setHint("Username")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)

                builder.setPositiveButton("Go to Main Menu"){ _, _ ->
                    var username1 = input.text.toString()
                    if(username1.isNotEmpty()){
                        println(username1)
                        var score1 = puanEkle.toString()
                        var time1 = textView5.text.toString()
                        var user1 = UserModel(username1,score1, time1)
                        db.insertData(user1)

                    }else{
                        Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(applicationContext, "Going to Main Menu", Toast.LENGTH_SHORT).show()
                    val mainPage = Intent(this@EasyMode, MainActivity::class.java)
                    startActivity(mainPage)
                }
                builder.setNeutralButton("Play Again"){ _, _ ->
                    var username = input.text.toString()
                    if(username.isNotEmpty()){
                        println(username)

                    }else{
                        Toast.makeText(applicationContext, "Username can't be null!", Toast.LENGTH_SHORT).show()
                    }
                    Toast.makeText(applicationContext, "Clicked Play Again", Toast.LENGTH_SHORT).show()
                    val easyMode = Intent(this@EasyMode, EasyMode::class.java)
                    startActivity(easyMode)

                }


                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

        }


    var puanEkle = 0
    var moves = 0
    var pairs = 0

    private fun updateViews() {
        val moveTextView: TextView = findViewById(R.id.textView7)
       //
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if(card.isMatched){
                button.alpha = 0.1f
                button.isClickable = false

            }
            button.setImageResource(if(card.isFaceUp) card.identifier else R.drawable.ic_baseline_ac_unit_24)
        }
       // pairs += 1
        moves += 1
       // pairs /= 2
        moveTextView.text= "Moves: $moves"
       // pairTextView.text= "Pairs: $pairs/8"

    }

    private fun updateModels(position: Int ) {
        val card = cards[position]
        if(card.isFaceUp){
            Toast.makeText(this,"invalid move", Toast.LENGTH_LONG).show()
            return
        }
        if(indexOfSingleSelectedCard == null){
            restoreCards()
            indexOfSingleSelectedCard = position
        }else{
            checkForMatch(indexOfSingleSelectedCard!!,position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {

        for(card in cards){
            if(!card.isMatched){
                card.isFaceUp = false
            }
        }

    }


    private fun checkForMatch(position1: Int, position2: Int) {

        val puantextView: TextView = findViewById(R.id.textView3)
        if(cards[position1].identifier == cards[position2].identifier){
            Toast.makeText(this,"Match Found!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
            puanEkle += 10
            puantextView.text= "Point: $puanEkle"

        }
        else{
            puanEkle -= 2
            puantextView.text= "Point: $puanEkle"

        }
    }
}
