package com.example.myapplicationdata.database

class SubscriberRepo(private val dao: SubscriberDAO) {

    val subscribers = dao.getAllSubscriber()

    suspend fun insert(subscriber: Subscriber):Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber):Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber):Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

}