package com.dicoding2.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FactoryViewModel private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FactoryViewModel? = null
        @JvmStatic
        fun getInstance(application: Application): FactoryViewModel {
            if (INSTANCE == null) {
                synchronized(FactoryViewModel::class.java) {
                    INSTANCE = FactoryViewModel(application)
                }
            }
            return INSTANCE as FactoryViewModel
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}