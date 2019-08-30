package com.example.myapplication.parser

import com.coroutine.sample.model.UserDetail
import java.io.Reader

abstract class Parser(val reader: Reader) {
    abstract fun parse(): List<UserDetail>
}