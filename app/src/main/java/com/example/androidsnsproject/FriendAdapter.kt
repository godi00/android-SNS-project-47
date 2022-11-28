package com.example.androidsnsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendAdapter(private val userList: ArrayList<Friend>): RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAdapter.FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)

        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendAdapter.FriendViewHolder, position: Int) {
        val user: Friend = userList[position]
        holder.name.text = user.name
        holder.email.text = user.email
        holder.birth.text = user.birth
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class FriendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.friend_name)
        val email: TextView = itemView.findViewById(R.id.friend_email)
        val birth: TextView = itemView.findViewById(R.id.friend_birth)
    }
}