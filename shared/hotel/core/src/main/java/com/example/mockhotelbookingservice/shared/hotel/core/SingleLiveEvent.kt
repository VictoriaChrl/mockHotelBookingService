package com.example.mockhotelbookingservice.shared.hotel.core

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    fun observe(owner: LifecycleOwner, observer: () -> Unit) {
        observe(owner, Observer { observer() })
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    operator fun invoke(value: T) {
        this.value = value
    }
}

class MutableLiveEvent : SingleLiveEvent<Unit>() {

    operator fun invoke() {
        value = Unit
    }
}

typealias LiveEvent = LiveData<Unit>