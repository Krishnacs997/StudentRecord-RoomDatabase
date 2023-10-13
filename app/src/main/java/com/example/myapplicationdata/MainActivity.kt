package com.example.myapplicationdata

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationdata.database.Subscriber
import com.example.myapplicationdata.database.SubscriberDatabase
import com.example.myapplicationdata.database.SubscriberRepo
import com.example.myapplicationdata.databinding.MainactivityBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding : MainactivityBinding
    private lateinit var subscriberViewModel: SubsViewModel
    //private lateinit var adapter: MyRecyclerViewAdapter
    private lateinit var adapter: StudentRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepo(dao)
        val factory = SubscriberViewMoelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubsViewModel::class.java]
        binding.myViewModel = subscriberViewModel

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        initRecyclerView()
    }

    private fun initRecyclerView() {

        adapter = StudentRecyclerView { selectedItem: Subscriber -> listItemClicked(selectedItem) }
        val mLayoutManager = LinearLayoutManager(this)
        binding.subscriberRecyclerView.layoutManager = mLayoutManager
        binding.subscriberRecyclerView.itemAnimator = DefaultItemAnimator()
        binding.subscriberRecyclerView.adapter = adapter

        displaySubscribersList()
    }

    private fun displaySubscribersList() {
        subscriberViewModel.getSavedSubscribers().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }
    private fun listItemClicked(subscriber: Subscriber) {
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}