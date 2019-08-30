

package com.chethan.coroutinesample.utils

import android.os.Handler
import android.os.Looper
import com.example.myapplication.parser.Parser
import com.chethan.coroutinesample.model.UserDetail
import java.util.concurrent.Executors

private const val ONE_SECOND = 1_000L

private const val ERROR_RATE = 0.3

private val executor = Executors.newCachedThreadPool()

private val uiHandler = Handler(Looper.getMainLooper())

/**
 * A internal file read that returns from a given list of user detail or an error.
 */
fun readLocally(parserStrategy: Parser): ReadInternalFile<List<UserDetail>> {

    val result = ReadInternalFile<List<UserDetail>>()


    // Launch the "File read request" in a new thread to avoid blocking the calling thread
    executor.submit {

        //TODO - remove time delay
        Thread.sleep(ONE_SECOND) //it is just to pretend we actually made a file read request by sleeping,

        val userDetailsList = parserStrategy.parse()

        if (userDetailsList.isEmpty()) {
            result.onError(OnReadFileException("Error in reading file"))
        } else {
            result.onSuccess(userDetailsList)
        }
    }
    return result
}


/**
 *  Calls used to observe results
 */
class ReadInternalFile<T> {
    var result: ReadInternalFileResult<T>? = null

    val listeners = mutableListOf<OnReadFileListener<T>>()

    fun addOnResultListener(listener: (ReadInternalFileResult<T>) -> Unit) {
        trySendResult(listener)
        listeners += listener
    }


    fun onSuccess(data: T) {
        result = OnReadFileSuccess(data)
        sendResultToAllListeners()
    }

    fun onError(throwable: Throwable) {
        result = OnReadFileError(throwable)
        sendResultToAllListeners()
    }

    /**
     * Broadcast the current result (success or error) to all registered listeners.
     */
    private fun sendResultToAllListeners() = listeners.map { trySendResult(it) }

    /**
     * Send the current result to a specific listener.
     *
     * If no result is set (null), this method will do nothing.
     */
    private fun trySendResult(listener: OnReadFileListener<T>) {
        val thisResult = result
        thisResult?.let {
            uiHandler.post {
                listener(thisResult)
            }
        }
    }
}

/**
 * File read result class that represents both success and errors
 */
sealed class ReadInternalFileResult<T>

/**
 * Passed to listener when the file read request was successful
 *
 * @param data the result
 */
class OnReadFileSuccess<T>(val data: T) : ReadInternalFileResult<T>()

/**
 * Passed to listener when the file read failed
 *
 * @param error the exception that caused this error
 */
class OnReadFileError<T>(val error: Throwable) : ReadInternalFileResult<T>()

/**
 * Listener "type" for observing a [ReadLocalFile]
 */
typealias OnReadFileListener<T> = (ReadInternalFileResult<T>) -> Unit

/**
 * Throwable to use in file read errors.
 */
class OnReadFileException(message: String) : Throwable(message)
