package com.coroutine.sample.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myapplication.parser.CsvParser
import com.google.android.material.snackbar.Snackbar
import com.coroutine.sample.R
import com.coroutine.sample.common.UserPostListAdapter
import com.coroutine.sample.databinding.UserDetailsFragmentBinding
import com.coroutine.sample.repository.UserDetailsRepository
import com.coroutine.sample.util.InternalDataFromFileImpl
import com.coroutine.sample.util.autoCleared


class UserDetailsFragment : Fragment() {

    var binding by autoCleared<UserDetailsFragmentBinding>()
    private var adapter by autoCleared<UserPostListAdapter>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.user_details_fragment,
                container,
                false
        )

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerAdapter = UserPostListAdapter() {
        }
        binding.userList.adapter = recyclerAdapter
        adapter = recyclerAdapter

        // Get user details repository
        val inputStream = resources.openRawResource(R.raw.issues)
        val parserStrategy = CsvParser(inputStream.reader())
        val repository = UserDetailsRepository(
            InternalDataFromFileImpl(parserStrategy)
        )
        val viewModel = ViewModelProviders
                .of(this, UserDetailsViewModel.FACTORY(repository))
                .get(UserDetailsViewModel::class.java)


        // update the title when the [UserDetailsViewModel.userDetails] changes
        viewModel.userDetails.observe(this, Observer { value ->
            adapter.submitList(value)
        })

        // show the spinner when [UserDetailsViewModel.spinner] is true
        viewModel.spinner.observe(this, Observer { value ->
            value?.let { show ->
                binding.spinner = show
            }
        })

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })

        viewModel.getUserDetails()

    }


    fun navController() = findNavController()

}