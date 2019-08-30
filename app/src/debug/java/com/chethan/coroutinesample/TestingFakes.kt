package com.chethan.coroutinesample

import com.chethan.coroutinesample.model.UserDetail
import com.chethan.coroutinesample.utils.InternalData
import com.chethan.coroutinesample.utils.OnReadFileException
import com.chethan.coroutinesample.utils.ReadInternalFile


/**
 * Testing mock implementation of file read
 */
class MockUserDetails(var call: ReadInternalFile<List<UserDetail>> = makeSuccessCall(
    TestUtil.createUserList()
)
) :
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
