package com.example.myapplicationdata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationdata.database.Subscriber
import com.example.myapplicationdata.databinding.ItemsRowBinding

class StudentRecyclerView(private val clickListener: (Subscriber) -> Unit) :  RecyclerView.Adapter<StudentRecyclerView.MyViewHolder>() {
    private val subscribersList = ArrayList<Subscriber>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemsRowBinding = ItemsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position], clickListener)
    }

    fun setList(subscribers: List<Subscriber>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)

    }

    class MyViewHolder(val binding: ItemsRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
            binding.nameText.text = subscriber.name
            binding.emailText.text = subscriber.email
            binding.mainLayout.setOnClickListener {
                clickListener(subscriber)
            }
        }

    }
}