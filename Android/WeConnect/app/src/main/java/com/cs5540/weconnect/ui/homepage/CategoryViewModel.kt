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
    private val  _categoryId = MutableLiveData<String>()
    val categoryId: LiveData<String>
        get() = _categoryId

    private val _categories = MutableLiveData<List<Category>>()
    val categories : LiveData<List<Category>>
        get() = _categories

    private val _navigateToProjects = MutableLiveData<String>()
    val navigateToProjects
        get() = _navigateToProjects

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

    fun onCategoryClicked(categoryId: String) {
        _categoryId.value = categoryId
        _navigateToProjects.value = categoryId
    }
    fun onProjectsNavigated() {
        _navigateToProjects.value = null
    }
}
