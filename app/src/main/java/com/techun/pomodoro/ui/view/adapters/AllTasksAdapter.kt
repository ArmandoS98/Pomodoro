package com.techun.pomodoro.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.ItemTaskBinding
import com.techun.pomodoro.domain.TaskItem

class AllTasksAdapter(private val context: Context?) :
    RecyclerView.Adapter<AllTasksAdapter.ViewHolder>() {
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
        holder.binding.tvTime.text = "${menuList[position].work_gap} minutes"
        holder.binding.tvLaps.text = "0/${menuList[position].no_of_tasks}"
        holder.binding.tvTimeLaps.text = "${menuList[position].short_breaks} min"
    }

    override fun getItemCount() = menuList.size

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val information = menuList[adapterPosition]
            findNavController(v).popBackStack(R.id.nav_pomodoro, false)
        }
    }
}