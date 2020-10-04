package com.denizd.solana.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Base ViewModel class that offers functions for basic asynchronous tasks.
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * Performs a given task asynchronously on the IO thread.
     *
     * @param   action the task to be performed
     */
    protected fun doAsync(action: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            action()
        }
    }

    /**
     * Performs a given task on the IO task, but blocking, so
     * that it can be executed and return the value of the task
     * like a regular function.
     *
     * @param   action the task to be performed
     * @return  the return value of the task
     */
    protected fun <T> returnBlocking(action: suspend () -> T): T =
        runBlocking(Dispatchers.IO) {
            action()
        }
}