package com.chethan.coroutinesample.utils

import com.example.myapplication.parser.Parser
import com.chethan.coroutinesample.model.UserDetail


interface InternalData {
    fun fetchUserData(): ReadInternalFile<List<UserDetail>>

}

/**
 * Default implementation of file read.
 */
class InternalDataFromFileImpl(val parser: Parser): InternalData {
    override fun fetchUserData() = readLocally(parser)
}
