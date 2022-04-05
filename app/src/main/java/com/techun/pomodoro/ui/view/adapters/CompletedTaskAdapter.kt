package com.techun.pomodoro.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.techun.pomodoro.R
import com.techun.pomodoro.data.model.TaskModel
import com.techun.pomodoro.databinding.ItemTaskBinding

class CompletedTaskAdapter (private val context: Context?) :
    RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>() {
    private var menuList: List<TaskModel> = listOf()

    fun setMenu(movieList: List<TaskModel>) {
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

        holder.binding.tvTitleTask.text = menuList[position].title
        holder.binding.tvTime.text = menuList[position].time
        holder.binding.tvLaps.text = menuList[position].laps
        holder.binding.tvTimeLaps.text = menuList[position].minutes
    }

    override fun getItemCount() = menuList.size

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}