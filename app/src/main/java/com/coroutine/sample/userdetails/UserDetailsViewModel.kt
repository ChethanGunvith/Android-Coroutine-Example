package com.coroutine.sample.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutine.sample.repository.UserDetailsError
import com.coroutine.sample.repository.UserDetailsRepository
import com.coroutine.sample.util.singleArgViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class UserDetailsViewModel(private val repository: UserDetailsRepository) : ViewModel() {

    companion object {
        val FACTORY = singleArgViewModelFactory(::UserDetailsViewModel)
    }

    /**
     * Request a snackbar to display a string.
     */
    private val _snackBar = MutableLiveData<String>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String>
        get() = _snackBar

    /**
     * Update user detail list text via this livedata
     */
    val userDetails = repository.userDetails

    private val _spinner = MutableLiveData<Boolean>()
    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner


    fun onSnackbarShown() {
        _snackBar.value = null
    }

    fun getUserDetails() {
        launchDataLoad {
            repository.getUserDetails()
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: UserDetailsError) {
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }
}
