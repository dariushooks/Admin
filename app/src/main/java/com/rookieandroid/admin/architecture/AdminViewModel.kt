package com.rookieandroid.admin.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rookieandroid.admin.Admin
import com.rookieandroid.admin.App.Companion.TYPE_ADMIN
import com.rookieandroid.admin.App.Companion.TYPE_HEADER
import com.rookieandroid.admin.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel()
{
    private val admins = MutableLiveData<List<Admin>>()
    private val moderators = MutableLiveData<List<Admin>>()
    private val coaches = MutableLiveData<List<Admin>>()

    init {
        loadAdmins()
        loadModerators()
        loadCoaches()
    }

    fun getAdmins() : LiveData<List<Admin>> = admins
    fun getModerators() : LiveData<List<Admin>> = moderators
    fun getCoaches() : LiveData<List<Admin>> = coaches

    private fun loadAdmins()
    {
        viewModelScope.launch {
            val list = arrayListOf(Admin("Admins", "",0, TYPE_HEADER),
                Admin("Kobe Bryant", "12/23/2008", R.drawable.kobe_bryant, TYPE_ADMIN),
                Admin("Chris Paul", "12/23/2008", R.drawable.chris_paul, TYPE_ADMIN),
                Admin("Tracy McGrady", "12/23/2008", R.drawable.tracy_mcgrady, TYPE_ADMIN),
                Admin("Michael Jordan", "12/23/2008", R.drawable.michael_jordan, TYPE_ADMIN))
            delay(2000)
            admins.value = list
        }
    }

    private fun loadModerators()
    {
        viewModelScope.launch {
            val list = arrayListOf(Admin("Moderators", "", 0, TYPE_HEADER),
                Admin("Phil Handy", "12/23/2008", R.drawable.phil_handy, TYPE_ADMIN),
                Admin("Kobe Bryant", "12/23/2008", R.drawable.kobe_bryant, TYPE_ADMIN),
                Admin("Lionel Hollins", "12/23/2008", R.drawable.lionel_hollins, TYPE_ADMIN))
            delay(2000)
            moderators.value = list
        }
    }

    private fun loadCoaches()
    {
        viewModelScope.launch {
            val list = //ArrayList<Admin>()
            arrayListOf(Admin("Coaches", "", 0, TYPE_HEADER),
                Admin("Gregg Popovich", "12/23/2008", R.drawable.gregg_poppovich, TYPE_ADMIN),
                Admin("Darvin Ham", "12/23/2008", R.drawable.darvin_ham, TYPE_ADMIN),
                Admin("Kobe Bryant", "12/23/2008", R.drawable.kobe_bryant, TYPE_ADMIN),
                Admin("Chauncey Billups", "12/23/2008", R.drawable.chauncey_billups, TYPE_ADMIN))
            delay(2000)
            coaches.value = list
        }
    }
}