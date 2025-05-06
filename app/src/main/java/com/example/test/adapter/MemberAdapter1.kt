package com.example.test.adapter

import android.animation.LayoutTransition
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.databinding.ListItemMemberBinding
import com.example.test.databinding.ListItemMemberNewBinding
import com.example.test.datamodel.Member

class MemberAdapter1(
    private val items: List<Member>,
    private val context: Context,
    private val new: Boolean,
    private val pos: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        private const val TYPE_NORMAL = 0
        private const val TYPE_SPECIAL = 1
    }


    private val animatedPositions = HashSet<Int>()

    interface OnItemClickListener {
        fun onItemClick(member: Member)
    }


    override fun getItemViewType(position: Int): Int {
        return if (new) TYPE_SPECIAL else TYPE_NORMAL
    }

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnClickListener(onClick: OnItemClickListener) {
        this.onItemClickListener = onClick
    }

    class ViewHolder(val binding: ListItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ViewHolderNew(val binding: ListItemMemberNewBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL) {
            val binding =
                ListItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolder(binding)
        } else {
            val binding =
                ListItemMemberNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ViewHolderNew(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        if (holder is ViewHolder) {

            holder.binding.member = items[position]
            holder.binding.executePendingBindings()

            if (!animatedPositions.contains(position) && position < 4) {
                startAnimation(holder.binding.innerLayout)
                animatedPositions.add(position)
            }

            if (items[position].isSelected) {
                holder.binding.innerLayout.setBackgroundResource(R.drawable.gradient_selected)
                holder.binding.textName.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                holder.binding.innerLayout.setBackgroundResource(R.drawable.gradient1)
                holder.binding.textName.setTextColor(ContextCompat.getColor(context, R.color.black))

            }

            holder.binding.root.setOnClickListener {
                modifyItem(position)
                onItemClickListener?.onItemClick(items[position])
            }

        } else if (holder is ViewHolderNew) {
            holder.binding.member = items[position]
            holder.binding.executePendingBindings()

            if (!animatedPositions.contains(position) && position < 4) { // شرط کنترلی که انیمیشن های آیتم ها فقط یک بار انجام شوند
                startAnimation(holder.binding.mainLayout)
                animatedPositions.add(position)
            }

            if (items[position].isSelected) {
                holder.binding.innerLayout.setBackgroundResource(R.drawable.back_member_new)
                holder.binding.textName.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.binding.textEye.setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                holder.binding.innerLayout.setBackgroundResource(R.drawable.gradient_white)
                holder.binding.textName.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.binding.textEye.setTextColor(ContextCompat.getColor(context, R.color.gray_text))
            }

            holder.binding.root.setOnClickListener {
                modifyItem(position)
                onItemClickListener?.onItemClick(items[position])
            }

        }


    }

    private fun modifyItem(position: Int){
        if (items[position].isSelected) {
            items[position].isSelected = false

        } else {
            items.forEachIndexed { index, item ->
                if (item.isSelected) {
                    item.isSelected = false
                    notifyItemChanged(index)
                }
            }
            items[position].isSelected = true
        }
        notifyItemChanged(position)

    }

    private fun startAnimation(view: View){
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
        animation.startOffset = (pos * 200).toLong()
        view.startAnimation(animation)
    }

    override fun getItemCount(): Int = items.size
}