package com.techun.pomodoro.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.techun.pomodoro.R
import com.techun.pomodoro.data.model.SettingOptionsModel
import com.techun.pomodoro.databinding.ItemSettingsBinding
import com.techun.pomodoro.ui.extensions.loadByResource

class SettingsOptionsAdapter :
    RecyclerView.Adapter<SettingsOptionsAdapter.ViewHolder>() {
    private var options: List<SettingOptionsModel> = listOf()

    fun setMenu(movieList: List<SettingOptionsModel>) {
        this.options = movieList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imgIconSettingsOptions.loadByResource(options[position].icon)
        holder.binding.tvTitleTask.text = options[position].title
    }

    override fun getItemCount() = options.size

    inner class ViewHolder(val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when (adapterPosition) {
                0 -> {
                    onItemClicked(v, R.id.nav_accountFragment)
                }
                1 -> {
                    onItemClicked(v, R.id.nav_accountFragment)
                }
                2 -> {
                    onItemClicked(v, R.id.nav_accountFragment)
                }
            }
        }
    }

    private fun onItemClicked(v: View, position: Int) {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.from_left)
            .setExitAnim(R.anim.to_right)
            .setPopEnterAnim(R.anim.from_right)
            .setPopExitAnim(R.anim.to_left)

        val opciones: NavOptions = builder.build()
        Navigation.findNavController(v).navigate(position, null, opciones)
    }
}