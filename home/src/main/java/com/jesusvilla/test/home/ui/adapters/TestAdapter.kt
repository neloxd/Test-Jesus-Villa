package com.jesusvilla.test.home.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusvilla.test.home.R
import com.jesusvilla.test.home.ui.model.PostModel

class TestAdapter(private val dataSet: ArrayList<PostModel>, private val onClickListener:(postModel: PostModel) ->  Unit) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val body = view.findViewById<TextView>(R.id.tvBody)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_test, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            holder.itemView.rootView.setOnClickListener { onClickListener(dataSet[position]) }
            //title.setOnClickListener { onClickListener(dataSet[position]) }
            title.text = "UserId:${dataSet[position].userId}"
            body.text = "IdPost:${(dataSet[position].id.toString())}"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<PostModel>) {
        this.dataSet.clear()
        this.dataSet.addAll(list)
        notifyDataSetChanged()
    }
}