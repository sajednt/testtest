package com.example.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ListItemMemberBinding
import com.example.test.datamodel.Member

class MemberAdapter() : ListAdapter<Member, MemberAdapter.EventViewHolder>(EventDiffCallback()) {

    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(member: Member)
    }

    fun setOnItemClickListener(onItemClick: OnItemClickListener){
        onItemClickListener = onItemClick
    }

    class EventViewHolder(private val binding: ListItemMemberBinding , private val onItemClick: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(member: Member) {
            binding.member = member
            binding.executePendingBindings()



            binding.root.setOnClickListener {
                onItemClick.onItemClick(member)
            }

        }
    }

    class EventDiffCallback : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ListItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding , onItemClickListener)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }
}