package com.jesusvilla.test.base.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class ConsumerLiveData<T> : MutableLiveData<T>() {
    private val atomicBool: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(
            owner
        ) { t ->
            if (hasActiveObservers() && atomicBool.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(value: T) {
        atomicBool.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T) {
        atomicBool.set(true)
        super.postValue(value)
    }
}
