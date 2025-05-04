package com.example.test.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ListItemMainBinding
import com.example.test.datamodel.Member
import com.example.test.datamodel.User
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class MainAdapter @AssistedInject constructor(@Assisted private val listener: OnItemClickListener)
    : ListAdapter<User, MainAdapter.EventViewHolder>(EventDiffCallback()) {


    interface OnItemClickListener {
        fun onItemClick(event: Member)
    }

    @AssistedFactory
    interface Factory {
        fun create(listener: OnItemClickListener): MainAdapter
    }

    class EventViewHolder(private val binding: ListItemMainBinding , private val onItemClick: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(members: User) {
//            binding.event = event
//            binding.executePendingBindings()
            Log.e("membersize",members.team.size.toString())
            var memberAdapter = MemberAdapter()

            memberAdapter.submitList(members.team)

            val layoutManager = object : LinearLayoutManager(binding.root.context, HORIZONTAL, false) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    val viewClass = 4.2f
                    lp?.width = (width / viewClass).toInt()
                    return true
                }
            }

            binding.recyclerItems.layoutManager = layoutManager
            binding.recyclerItems.apply {
                memberAdapter.setOnItemClickListener(object : MemberAdapter.OnItemClickListener {
                    override fun onItemClick(member: Member) {
                        binding.root.setOnClickListener {
                            onItemClick.onItemClick(member)
                        }
                    }
                })
                adapter = memberAdapter
                scrollToPosition(0)
            }


        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ListItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding , listener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}