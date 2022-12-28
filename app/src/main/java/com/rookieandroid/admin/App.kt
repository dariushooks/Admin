package com.rookieandroid.admin

import android.app.Application

class App : Application()
{
    companion object{
        const val TYPE_HEADER = 0
        const val TYPE_ADMIN = 1
        const val TYPE_SEARCH = 2
    }
}