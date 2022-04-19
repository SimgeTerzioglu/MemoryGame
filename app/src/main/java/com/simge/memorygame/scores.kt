package com.simge.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simge.memorygame.databinding.ActivityScoresBinding

class scores : AppCompatActivity() {
    companion object{
        lateinit var dbHandler: DataBaseHelper
    }
    private lateinit var scoresBinding: ActivityScoresBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scoresBinding = ActivityScoresBinding.inflate(layoutInflater)
        setContentView(scoresBinding.root)

        dbHandler = DataBaseHelper(this)
        viewUsers()
    }
    private fun viewUsers(){
        val usersList = dbHandler.getUsers(this)
        val adapter = ScoreAdapter(this,usersList)
        val rv: RecyclerView = findViewById(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rv.adapter = adapter

    }

}