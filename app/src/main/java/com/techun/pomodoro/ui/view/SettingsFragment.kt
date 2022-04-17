package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.techun.pomodoro.R
import com.techun.pomodoro.data.model.SettingOptionsModel
import com.techun.pomodoro.databinding.FragmentSettingsBinding
import com.techun.pomodoro.ui.view.adapters.AllTasksAdapter
import com.techun.pomodoro.ui.view.adapters.CompletedTaskAdapter
import com.techun.pomodoro.ui.view.adapters.SettingsOptionsAdapter

class SettingsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: SettingsOptionsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgBackArrow.setOnClickListener(this)

        //Config Options
        val options = listOf(
            SettingOptionsModel(R.drawable.outline_account_circle_24, "Account"),
            SettingOptionsModel(R.drawable.outline_access_alarm_24, "Reminders"),
            SettingOptionsModel(R.drawable.outline_help_outline_24, "Support")
        )

        SettingsOptionsAdapter().setMenu(options)

        //Metodo el recyclerview
        recyclerInit(options)
    }

    private fun recyclerInit(options: List<SettingOptionsModel>) {
        adapter = SettingsOptionsAdapter()
        adapter.setMenu(options)
        binding.recSettings.layoutManager = LinearLayoutManager(context)
        binding.recSettings.adapter = adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgBackArrow -> {
                findNavController().popBackStack(R.id.nav_profile, false)
            }
        }
    }

}