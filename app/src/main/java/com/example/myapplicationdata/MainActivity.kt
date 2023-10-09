package com.example.myapplicationdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationdata.database.SubscriberDatabase
import com.example.myapplicationdata.database.SubscriberRepo
import com.example.myapplicationdata.databinding.MainactivityBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding : MainactivityBinding
    private lateinit var subscriberViewModel: SubsViewModel
    //private lateinit var adapter: MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepo(dao)
        val factory = SubscriberViewMoelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory)[SubsViewModel::class.java]
        binding.myViewModel = subscriberViewModel
        //binding.lifecycleOwner = this

    }
}