package com.coroutine.sample

import com.coroutine.sample.model.UserDetail
import com.coroutine.sample.util.InternalData
import com.coroutine.sample.util.OnReadFileException
import com.coroutine.sample.util.ReadInternalFile


/**
 * Testing mock implementation of file read
 */
class MockUserDetails(var call: ReadInternalFile<List<UserDetail>> = makeSuccessCall(TestUtil.createUserList())) :
    InternalData {
    override fun fetchUserData(): ReadInternalFile<List<UserDetail>> {
        return call
    }
}

/**
 * Make successful read result
 *
 * @param result result to return
 */
fun <T> makeSuccessCall(result: T) = ReadInternalFile<T>().apply {
    onSuccess(result)
}

/**
 * @param throwable error to wrap
 */
fun makeFailureCall(throwable: OnReadFileException) = ReadInternalFile<List<UserDetail>>().apply {
    onError(throwable)
}
