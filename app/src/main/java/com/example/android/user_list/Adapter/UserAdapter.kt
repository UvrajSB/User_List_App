package com.example.android.user_list.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.user_list.DataClass.UserData
import com.example.android.user_list.R

class UserAdapter: RecyclerView.Adapter<UserViewHolder>() {
    var UsersArray : ArrayList<UserData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var CurrentUser = UsersArray[position]
        holder.name.text =CurrentUser.name
        holder.email.text = CurrentUser.email
        Glide.with(holder.itemView.context).load(CurrentUser.imageUrl).circleCrop().into(holder.image)
    }

    override fun getItemCount(): Int {
        return UsersArray.size
    }

    fun updateUsers(Users: ArrayList<UserData>) {
        UsersArray.clear()
        UsersArray.addAll(Users)

        notifyDataSetChanged()

    }
}

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val name = itemView.findViewById<TextView>(R.id.user_name)
    val email = itemView.findViewById<TextView>(R.id.user_email)
    val image = itemView.findViewById<ImageView>(R.id.user_image)
}