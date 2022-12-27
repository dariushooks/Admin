package com.rookieandroid.admin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rookieandroid.admin.Admin
import com.rookieandroid.admin.App.Companion.TYPE_HEADER
import com.rookieandroid.admin.R

class SearchAdminAdapter(private val admins : ArrayList<Admin>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return if(viewType == TYPE_HEADER)
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_header, parent, false)
            HeaderViewHolder(view, admins)
        }

        else
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.admin_item, parent, false)
            AdminViewHolder(view, admins)
        }
    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int)
    {
        if(admins[position].type == TYPE_HEADER)
            (holder as HeaderViewHolder).bind(position)
        else
            (holder as AdminViewHolder).bind(position)
    }

    override fun getItemViewType(position: Int): Int = admins[position].type

    override fun getItemCount(): Int = admins.size

    class HeaderViewHolder(itemView : View, private val admins: ArrayList<Admin>) : RecyclerView.ViewHolder(itemView)
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
        //private val checkbox : CheckBox = itemView.findViewById(R.id.admin_checkbox)

        fun bind(position : Int)
        {
            name.text = admins[position].name
            date.text = admins[position].date
        }
    }
}