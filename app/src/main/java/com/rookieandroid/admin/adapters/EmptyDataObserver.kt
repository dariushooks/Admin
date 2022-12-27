package com.rookieandroid.admin.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(private val recyclerView : RecyclerView, private val view : View) : RecyclerView.AdapterDataObserver()
{
    init { checkIfEmpty() }

    private fun checkIfEmpty()
    {
        val empty = recyclerView.adapter?.itemCount == 0
        if(empty)
        {
            view.isVisible = true
            recyclerView.isVisible = false
        }

        else
        {
            view.isVisible = false
            recyclerView.isVisible = true
        }

    }

    override fun onChanged()
    {
        super.onChanged()
        checkIfEmpty()
    }
}