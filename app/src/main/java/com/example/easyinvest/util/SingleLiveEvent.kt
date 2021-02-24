package com.example.easyinvest.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleLiveEvent<T>() : MutableLiveData<T>() {
    constructor(value: T) : this() {
        this.value = value
    }

    private val pendingMap: MutableMap<String, Boolean> = HashMap()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        pendingMap.putIfNotAlreadyThere(owner.javaClass.name, false)
        super.observe(owner, Observer {
            if (pendingMap[owner.javaClass.name] == true) {
                observer.onChanged(it)
                pendingMap[owner.javaClass.name] = false
            }
        })
    }

    override fun setValue(t: T?) {
        pendingMap.forEach { pendingMap[it.key] = true }
        super.setValue(t)
    }

    fun <T, V> MutableMap<T, V>.putIfNotAlreadyThere(key: T, value: V) {
        if (get(key) == null) this[key] = value
    }
}