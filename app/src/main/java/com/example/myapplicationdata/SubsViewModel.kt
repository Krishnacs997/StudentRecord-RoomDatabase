package com.example.myapplicationdata

import android.app.Activity
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.myapplicationdata.database.Subscriber
import com.example.myapplicationdata.database.SubscriberRepo
import kotlinx.coroutines.launch

class SubsViewModel(private val repo: SubscriberRepo) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage
    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }
    fun saveOrUpdate() {
        inputName.value = "krishna"
        inputEmail.value = "Krishna@gmail.com"

        if(inputName.value == null){
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null){
            statusMessage.value = Event("Please enter subscriber's email")
        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Please enter a correct email address")
        } else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            insertSubscriber(Subscriber(0, name, email))
            Log.e("Record Added", "$name $email")
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
       val newRowId = repo.insert(subscriber)
        if(newRowId > -1){
        Log.d("Record Added", "Recorded Added")
        } else{
            Log.e("Record Added", "Recorded not Added")
            //Toast.makeText(application, "Error occurred", Toast.LENGTH_SHORT).show()
        }

    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteSubscriber(subscriberToUpdateOrDelete)
        } else {
            clearAll()
        }
    }
    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRowsDeleted = repo.delete(subscriber)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
    fun getSavedSubscribers() = liveData {
        repo.subscribers.collect {
            emit(it)
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repo.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
}