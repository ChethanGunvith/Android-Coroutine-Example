package com.coroutine.sample.util

import com.example.myapplication.parser.Parser
import com.coroutine.sample.model.UserDetail


interface InternalData {
    fun fetchUserData(): ReadInternalFile<List<UserDetail>>

}

/**
 * Default implementation of file read.
 */
class InternalDataFromFileImpl(val parser: Parser): InternalData {
    override fun fetchUserData() = readLocally(parser)
}
