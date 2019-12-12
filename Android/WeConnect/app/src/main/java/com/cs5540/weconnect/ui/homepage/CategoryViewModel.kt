package com.cs5540.weconnect.ui.homepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cs5540.weconnect.network.WeConnectApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories : LiveData<List<Category>>
        get() = _categories

    private var categoryViewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        categoryViewModelJob + Dispatchers.Main)


    init {
        getWeConnectCategories()
    }

    /**
     * Sets the value of the status LiveData to the WeConnect categories.
     */
    private fun getWeConnectCategories() {
        coroutineScope.launch {
            var getCategoriesDeferred = WeConnectApi.retrofitService.getCategories()
            try {
                _categories.value = getCategoriesDeferred.await()

            } catch (e: Exception) {
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        categoryViewModelJob.cancel()
    }
}
