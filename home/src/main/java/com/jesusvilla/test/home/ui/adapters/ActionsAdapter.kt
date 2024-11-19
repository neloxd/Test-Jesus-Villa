package com.jesusvilla.test.home.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusvilla.test.home.R
import com.jesusvilla.test.home.ui.model.UserActionsModel

class ActionsAdapter(private val dataSet: List<UserActionsModel>) :
    RecyclerView.Adapter<ActionsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.iv_user_action)
        val title = view.findViewById<TextView>(R.id.tv_action)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.actions_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            image.setImageResource(dataSet[position].icon)
            title.setText(dataSet[position].title)
        }
    }
}
