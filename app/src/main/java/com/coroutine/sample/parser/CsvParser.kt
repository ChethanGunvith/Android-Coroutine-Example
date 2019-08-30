package com.example.myapplication.parser

import com.opencsv.CSVReaderBuilder
import com.coroutine.sample.model.UserDetail
import java.io.Reader

class CsvParser(reader: Reader) : Parser(reader) {

    override fun parse(): List<UserDetail> {
        val csvReader = CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build()
        val userDetailsList = mutableListOf<UserDetail>()
        var record = csvReader.readNext()

        while (record != null) {
            userDetailsList.add(
                UserDetail(
                    record[0],
                    record[1],
                    record[2].toInt(),
                    record[3]
                )
            )
            record = csvReader.readNext()
        }
        return userDetailsList
    }

}