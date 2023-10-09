package com.example.myapplicationdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationdata.database.SubscriberRepo

class SubscriberViewMoelFactory(private var repository: SubscriberRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass == SubsViewModel::class.java) {
            SubsViewModel(repository) as T
        }
        else {
            super.create(modelClass)
        }
    }

   /* fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass == SubsViewModel::class.java) {
                SubsViewModel(repository) as T
             }
            else {
                super.create(modelClass)
            }
         }*/
            /*if(modelClass.isAssignableFrom(SubsViewModel::class.java)){
                return SubsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown View Model class")
        }*/
    }
