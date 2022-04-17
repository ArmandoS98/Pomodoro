package com.techun.pomodoro.ui.view.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.techun.pomodoro.R
import com.techun.pomodoro.data.model.TaskModel
import com.techun.pomodoro.databinding.ItemTaskBinding


class AllTasksAdapter(private val context: Context?) :
    RecyclerView.Adapter<AllTasksAdapter.ViewHolder>() {
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

        holder.binding.imgCloseTask.setImageDrawable(
            ContextCompat.getDrawable(
                context!!,
                R.drawable.outline_play_arrow_24
            )
        )
        holder.binding.viewPriority.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                when (menuList[position].priority) {
                    0 -> R.drawable.circle_red
                    1 -> R.drawable.circle_yellow
                    else -> R.drawable.circle_green
                }
            )
        )

        holder.binding.tvTitleTask.text = menuList[position].name
        holder.binding.tvTime.text = menuList[position].short_breaks.toString()
        holder.binding.tvLaps.text = menuList[position].no_of_tasks.toString()
        holder.binding.tvTimeLaps.text = menuList[position].short_breaks.toString()
    }

    override fun getItemCount() = menuList.size

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)
}