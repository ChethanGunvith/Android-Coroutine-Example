package com.chethan.coroutinesample

import com.chethan.coroutinesample.model.UserDetail


/**
 * Created by Chethan on 7/30/2019.
 */
object TestUtil {

    fun createUserList(): List<UserDetail> {
        val list: ArrayList<UserDetail> = arrayListOf<UserDetail>()

        list.add(
            UserDetail(
                "Theo",
                "Jansen",
                5,
                "1978-01-02T00:00:00"
            )
        )

        list.add(
            UserDetail(
                "Fiona",
                "de Vries",
                7,
                "1950-11-12T00:00:00"
            )
        )


        list.add(
            UserDetail(
                "Petra",
                "Boersma",
                1,
                "2001-04-20T00:00:00"
            )
        )

        return list
    }
}



