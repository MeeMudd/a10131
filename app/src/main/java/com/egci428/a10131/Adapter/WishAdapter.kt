package com.egci428.a10131.Adapter


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egci428.a10131.R
import com.egci428.a10131.Model.Wish



data class WishAdapter (private val wishObject: ArrayList<Wish>, val context: Context):RecyclerView.Adapter<WishAdapter.WishViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wishlist,parent,false)
        return WishViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return wishObject.size
    }

    override fun onBindViewHolder(holder: WishViewHolder, position: Int) {

        if(wishObject.get(position).status=="positive"){
            holder.msgTxt.setTextColor(Color.parseColor("#009DCF"))
        }else if(wishObject.get(position).status=="negative"){
            holder.msgTxt.setTextColor(Color.parseColor("#FF7518"))
        }
        holder.msgTxt.text = wishObject.get(position).message
        holder.dateTxt.text = wishObject.get(position).date
        val res = context.resources.getIdentifier("opened_cookie", "drawable", context.packageName)
        holder.image.setImageResource(res)
    }


    class WishViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var msgTxt = itemView.findViewById<TextView>(R.id.wishText)
        var image = itemView.findViewById<ImageView>(R.id.openCookie)
        var dateTxt = itemView.findViewById<TextView>(R.id.dateAndTimeText)

    }
}