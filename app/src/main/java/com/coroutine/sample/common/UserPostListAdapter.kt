package com.coroutine.sample.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.coroutine.sample.R
import com.coroutine.sample.databinding.UserDetailItemBinding
import com.coroutine.sample.model.UserDetail


class UserPostListAdapter(
        private val repoClickCallback: ((UserDetail) -> Unit)?

) : DataBoundListAdapter<UserDetail, UserDetailItemBinding>(

        diffCallback = object : DiffUtil.ItemCallback<UserDetail>() {
            override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem.firstName == newItem.firstName
                        && oldItem.surName == newItem.surName
            }

            override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
                return oldItem.firstName == newItem.firstName
                        && oldItem.surName == newItem.surName
            }
        }
) {

    override fun createBinding(parent: ViewGroup): UserDetailItemBinding {
        val binding = DataBindingUtil.inflate<UserDetailItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.user_detail_item,
                parent,
                false
        )
        binding.root.setOnClickListener {
            binding.userDetail?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: UserDetailItemBinding, item: UserDetail) {
        binding.userDetail = item
    }
}
