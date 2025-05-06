package com.example.test.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.ListItemMainBinding
import com.example.test.datamodel.Member

class MainAdapter1(private var items: MutableList<List<Member>>, val context: Context , val new: Boolean) :
    RecyclerView.Adapter<MainAdapter1.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(member: Member, position: Int)
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnClickListener(onClick: OnItemClickListener) {
        this.onItemClickListener = onClick
    }

    inner class ViewHolder(val binding: ListItemMainBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMainBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val member = items[position]

        val memberAdapter = MemberAdapter1(member, context , new ,position)

        holder.binding.textCounter.text = (position + 1).toString()

        val layoutManager : LinearLayoutManager

        if(new){
            holder.binding.textCounter.visibility = View.GONE
             layoutManager = linearLayoutViewClass(1.6f)
        }
        else {
            layoutManager = linearLayoutViewClass(3.5f)
        }
        holder.binding.recyclerItems.layoutManager = layoutManager

        holder.binding.recyclerItems.apply {
            memberAdapter.setOnClickListener(object : MemberAdapter1.OnItemClickListener {
                override fun onItemClick(member: Member) {
                    onItemClickListener?.onItemClick(member, position)
                }
            })
            adapter = memberAdapter
        }
    }

    fun addItem(list: List<Member>){
        items.add(list)
        notifyItemInserted(items.size-1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(item: MutableList<List<Member>>) {
        items = item
        notifyDataSetChanged()
    }

    private fun linearLayoutViewClass(float: Float): LinearLayoutManager{
         val layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
            override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                lp?.width = (width / float).toInt()
                return true
            }
        }
        return  layoutManager
    }

    override fun getItemCount(): Int = items.size
}