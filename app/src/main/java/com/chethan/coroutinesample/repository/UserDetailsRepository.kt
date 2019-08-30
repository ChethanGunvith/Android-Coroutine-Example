package com.chethan.coroutinesample.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chethan.coroutinesample.model.UserDetail
import com.chethan.coroutinesample.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * UserDetailsRepository provides an interface to fetch list of user details.
 */
class UserDetailsRepository(val internalData: InternalData) {


    private val _userDetails = MutableLiveData<List<UserDetail>>()
    val userDetails: LiveData<List<UserDetail>> get() = _userDetails

    suspend fun getUserDetails() {
        withContext(Dispatchers.IO) {
            try {
                val result = internalData.fetchUserData().await()
                _userDetails.postValue(result)
            } catch (error: OnReadFileException) {
                throw UserDetailsError(error)
            }
        }
    }
}


class UserDetailsError(cause: Throwable) : Throwable(cause.message, cause)

suspend fun <T> ReadInternalFile<T>.await(): T {
    return suspendCoroutine { continuation ->
        addOnResultListener { result ->
            when (result) {
                is OnReadFileSuccess<T> -> continuation.resume(result.data)
                is OnReadFileError -> continuation.resumeWithException(result.error)
            }
        }
    }
}
