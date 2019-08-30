package com.chethan.coroutinesample

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.CoroutinesTestRule
import com.chethan.coroutinesample.repository.UserDetailsRepository
import com.chethan.coroutinesample.userdetails.UserDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class UserDetailsViewModelUnitTest {

    private lateinit var subject: UserDetailsViewModel


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()



    @ExperimentalCoroutinesApi
    @Test
    fun refreshUserDetails_MockitoVersion() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val mockRepository = mock(UserDetailsRepository::class.java)
        subject = UserDetailsViewModel(mockRepository).also {
            it.getUserDetails()
        }

        verify(mockRepository).getUserDetails()
    }
}