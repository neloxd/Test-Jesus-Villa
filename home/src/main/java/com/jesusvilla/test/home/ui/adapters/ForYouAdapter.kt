package com.jesusvilla.test.home.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusvilla.test.home.R
import com.jesusvilla.test.home.ui.model.ForYouModel

class ForYouAdapter(private val dataSet: List<ForYouModel>) :
    RecyclerView.Adapter<ForYouAdapter.ViewHolder>() {
    var onItemClick: ((ForYouModel) -> Unit)? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.iv_for_you)
        val title = view.findViewById<TextView>(R.id.tv_for_you_title)
        val description = view.findViewById<TextView>(R.id.tv_for_you_subtitle)
        val link = view.findViewById<TextView>(R.id.tv_for_you_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.for_you_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            image.setImageResource(dataSet[position].img)
            title.setText(dataSet[position].title)
            description.setText(dataSet[position].description)
            link.setText(dataSet[position].linkText)
            holder.itemView.setOnClickListener {
                onItemClick?.invoke(dataSet[position])
            }
        }
    }
}
