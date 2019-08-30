package com.chethan.coroutinesample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chethan.coroutinesample.model.UserDetail
import com.chethan.coroutinesample.repository.UserDetailsRepository
import com.chethan.coroutinesample.userdetails.UserDetailsViewModel
import com.chethan.coroutinesample.MockUserDetails
import com.chethan.coroutinesample.utils.OnReadFileException
import com.chethan.coroutinesample.utils.ReadInternalFile
import com.chethan.coroutinesample.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()



    @Test
    fun whenSuccessUserDetailsLoad_itShowsAndHidesSpinner() {
        val call = ReadInternalFile<List<UserDetail>>()

        val subject = UserDetailsViewModel(
            UserDetailsRepository(
                MockUserDetails(call)
            )
        )

        subject.spinner.captureValues {
            subject.getUserDetails()
            runBlocking {
                assertSendsValues(2_000, true)
                call.onSuccess(TestUtil.createUserList())
                assertSendsValues(2_000, true, false)
            }
        }
    }

    @Test
    fun whenErrorTitleReload_itShowsErrorAndHidesSpinner() {
        val call = ReadInternalFile<List<UserDetail>>()
        val subject = UserDetailsViewModel(
            UserDetailsRepository(
                MockUserDetails(call)
            )
        )

        subject.spinner.captureValues {
            val spinnerCaptor = this
            subject.getUserDetails()
            runBlocking {
                assertSendsValues(2_000, true)
                call.onError(OnReadFileException("An error"))
                assertSendsValues(2_000, true, false)
            }
        }
    }

    @Test
    fun whenErrorUserDetailsReload_itShowsErrorText() {
        val call = ReadInternalFile<List<UserDetail>>()
        val subject = UserDetailsViewModel(
            UserDetailsRepository(
                MockUserDetails(call)
            )
        )

        subject.snackbar.captureValues {
            val spinnerCaptor = this
            subject.getUserDetails()
            runBlocking {
                call.onError(OnReadFileException("An error"))
                assertSendsValues(2_000, "An error")
                subject.onSnackbarShown()
                assertSendsValues(2_000, "An error", null)
            }
        }
    }

}
