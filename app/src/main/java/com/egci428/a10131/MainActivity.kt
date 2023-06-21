package com.egci428.a10131

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egci428.a10131.Model.Wish
import com.egci428.a10131.Adapter.WishAdapter


class MainActivity : AppCompatActivity() {

    lateinit var plusBtn: ImageButton
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: WishAdapter
    lateinit var wishArray: ArrayList<Wish>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plusBtn = findViewById(R.id.plusBtn)
        recyclerView = findViewById(R.id.recyclerView)
        wishArray = ArrayList()

        adapter = WishAdapter(wishArray, this)
        var linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


        plusBtn.setOnClickListener {
            var intent = Intent(this, NewFortuneCookies::class.java)
            startActivityForResult(intent, 0)

        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                wishArray.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if(resultCode==Activity.RESULT_OK){

            var msg = data?.getStringExtra("messageIntent").toString()
            var status= data?.getStringExtra("statusIntent").toString()
            var date = data?.getStringExtra("dateIntent").toString()

            wishArray.add(Wish(msg,status,date))
            adapter.notifyDataSetChanged()
        }
    }
}