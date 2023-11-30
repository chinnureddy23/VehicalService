package com.example.vechiceserviceapp.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vechiceserviceapp.R

class MessageAdapter(var messageModelList: List<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_layout, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageModelList[position]
        if (message.getSentBy() == MessageModel.SENT_BY_ME) {
            holder.top_chat_layout.visibility = View.VISIBLE
            holder.top_text.text = message.getMessage()
            holder.bottom_chat_layout.visibility = View.GONE
            holder.username.text = "Me"
            holder.sener_image.setImageResource(R.drawable.user)
        } else {
            holder.top_chat_layout.visibility = View.GONE
            holder.bottom_tex.text = message.getMessage()
            holder.bottom_chat_layout.visibility = View.VISIBLE
            holder.username.text = "Vechicle Service App"
            holder.sener_image.setImageResource(R.drawable.baseline_directions_car_24)
        }
    }

    override fun getItemCount(): Int {
        return messageModelList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var top_chat_layout: LinearLayout
        var bottom_chat_layout: LinearLayout
        var top_text: TextView
        var bottom_tex: TextView
        var username: TextView
        var sener_image: ImageView

        init {
            top_chat_layout = itemView.findViewById(R.id.top_chatview)
            bottom_chat_layout = itemView.findViewById(R.id.bottom_chatview)
            top_text = itemView.findViewById(R.id.top_text)
            bottom_tex = itemView.findViewById(R.id.bottom_text)
            username = itemView.findViewById(R.id.txt_name)
            sener_image = itemView.findViewById(R.id.profile_image)
        }
    }
}