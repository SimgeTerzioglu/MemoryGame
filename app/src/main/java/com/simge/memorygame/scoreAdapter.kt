package com.simge.memorygame
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simge.memorygame.databinding.ScoreItemsBinding

class ScoreAdapter (mContext: Context,val users : ArrayList<UserModel>):RecyclerView.Adapter<ScoreAdapter.ViewHolder>(){


    val myContext = mContext

    class ViewHolder(val contactItemBinding: ScoreItemsBinding) :
        RecyclerView.ViewHolder(contactItemBinding.root) {
        val txtKullaniciAdi = contactItemBinding.textViewKullaniciAdi
        val txtScore = contactItemBinding.textViewScore
        val txtTime = contactItemBinding.textViewTime
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ScoreItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val users : UserModel = users[position]
        holder.txtKullaniciAdi.text = users.username
        holder.txtScore.text = users.score
        holder.txtTime.text = users.time

    }

    override fun getItemCount(): Int {
        return users.size
    }
}