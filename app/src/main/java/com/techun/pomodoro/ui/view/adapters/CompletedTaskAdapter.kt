package com.techun.pomodoro.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ItemTaskBinding
import com.techun.pomodoro.domain.TaskItem

class CompletedTaskAdapter(private val context: Context?) :
    RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>() {
    private var menuList: List<TaskItem> = listOf()

    fun setMenu(movieList: List<TaskItem>) {
        this.menuList = movieList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.viewPriority.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context!!,
                R.drawable.circle_green
            )
        )

        holder.binding.tvTitleTask.text = menuList[position].name
        holder.binding.tvTime.text = menuList[position].work_gap.toString()
        holder.binding.tvLaps.text = menuList[position].no_of_tasks.toString()
        holder.binding.tvTimeLaps.text = menuList[position].short_breaks.toString()
    }

    override fun getItemCount() = menuList.size

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}