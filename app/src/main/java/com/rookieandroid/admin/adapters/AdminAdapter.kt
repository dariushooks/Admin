package com.rookieandroid.admin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rookieandroid.admin.Admin
import com.rookieandroid.admin.App.Companion.TYPE_ADMIN
import com.rookieandroid.admin.App.Companion.TYPE_HEADER
import com.rookieandroid.admin.App.Companion.TYPE_SEARCH
import com.rookieandroid.admin.R

class AdminAdapter(private val admins : ArrayList<Admin>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return if(viewType == TYPE_HEADER)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_header, parent, false)
            HeaderViewHolder(view, admins)
        }

        else if(viewType == TYPE_ADMIN)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_item, parent, false)
            AdminViewHolder(view, admins)
        }

        else
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_header, parent, false)
            SearchViewHolder(view, admins)
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int)
    {
        when(admins[position].type)
        {
            TYPE_HEADER -> {(holder as HeaderViewHolder).bind(position)}
            TYPE_ADMIN -> {(holder as AdminViewHolder).bind(position)}
            TYPE_SEARCH -> {(holder as SearchViewHolder).bind(position)}
        }

    }

    override fun getItemViewType(position: Int): Int = admins[position].type

    override fun getItemCount(): Int = admins.size

    class HeaderViewHolder(itemView : View, private val admins: ArrayList<Admin>) : RecyclerView.ViewHolder(itemView)
    {
        private val header : TextView = itemView.findViewById(R.id.title_header)


        fun bind(position : Int)
        {
            header.text = admins[position].name
        }
    }

    class SearchViewHolder(itemView : View, private val admins: ArrayList<Admin>) : RecyclerView.ViewHolder(itemView)
    {
        private val header : TextView = itemView.findViewById(R.id.title_header)
        private val add : ImageView = itemView.findViewById(R.id.add_admin)

        fun bind(position : Int)
        {
            header.text = admins[position].name
            add.isVisible = false
        }
    }

    class AdminViewHolder(itemView : View, private val admins: ArrayList<Admin>) : RecyclerView.ViewHolder(itemView)
    {
        private val name : TextView = itemView.findViewById(R.id.admin_name)
        private val date : TextView = itemView.findViewById(R.id.admin_date)
        private val image : ImageView = itemView.findViewById(R.id.admin_image)

        fun bind(position : Int)
        {
            name.text = admins[position].name
            date.text = admins[position].date
            Glide.with(itemView).load(admins[position].image).placeholder(R.drawable.ic_nav_admin).into(image)
        }
    }
}