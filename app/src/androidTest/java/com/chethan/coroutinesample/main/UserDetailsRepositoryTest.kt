package com.chethan.coroutinesample

import com.chethan.coroutinesample.utils.OnReadFileException
import com.google.common.truth.Truth
import com.chethan.coroutinesample.repository.await
import com.chethan.coroutinesample.TestUtil
import com.chethan.coroutinesample.makeFailureCall
import com.chethan.coroutinesample.makeSuccessCall
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDetailsRepositoryTest {

    @Test
    fun whenFileReadCallSuccess_resumeWithResult() {
        val subject =
            makeSuccessCall(TestUtil.createUserList())
        runBlocking {
            Truth.assertThat(subject.await().get(0).firstName).isEqualTo("Theo")
            Truth.assertThat(subject.await().get(1).firstName).isEqualTo("Fiona")
            Truth.assertThat(subject.await().get(2).firstName).isEqualTo("Petra")
        }
    }

    @Test(expected = OnReadFileException::class)
    fun whenFileReadCallFailure_throws() {
        val subject = makeFailureCall(OnReadFileException("the error"))
        runBlocking {
            subject.await()
        }
    }
}
