package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.techun.pomodoro.R
import com.techun.pomodoro.data.model.TaskModel
import com.techun.pomodoro.databinding.FragmentTasksBinding
import com.techun.pomodoro.ui.view.adapters.AllTasksAdapter
import com.techun.pomodoro.ui.view.adapters.CompletedTaskAdapter

class TasksFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: AllTasksAdapter
    lateinit var completedTaskadapter: CompletedTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnmNewTask.setOnClickListener(this)
        super.onViewCreated(view, savedInstanceState)
        val allTasks = emptyList<TaskModel>() /*listOf(
            TaskModel(
                "Mobile app design",
                "30 minutes",
                "1/4",
                "25 min",
                0
            ),
            TaskModel(
                "Ui animation",
                "0 minutes",
                "0/2",
                "25 min",
                1
            ),
            TaskModel(
                "Study languages",
                "0 minutes",
                "0/1",
                "25 min",
                2
            )
        )*/
        val completedTasts = emptyList<TaskModel>() /*listOf(
            TaskModel(
                "Onboarding",
                "1 h 30 min",
                "3/4",
                "25 min",
                2
            )
        )*/
        AllTasksAdapter(context).setMenu(allTasks)
        CompletedTaskAdapter(context).setMenu(completedTasts)

        //Metodo el recyclerview
        recyclerInit(allTasks, completedTasts)
    }

    private fun recyclerInit(temp: List<TaskModel>, completedTasts: List<TaskModel>) {
        adapter = AllTasksAdapter(context)
        adapter.setMenu(temp)
        binding.rvAllTasks.layoutManager = LinearLayoutManager(context)

        binding.rvAllTasks.adapter = adapter

        //Completed Tasks
        completedTaskadapter = CompletedTaskAdapter(context)
        completedTaskadapter.setMenu(completedTasts)
        binding.rvCompletedTasks.layoutManager = LinearLayoutManager(context)

        binding.rvCompletedTasks.adapter = completedTaskadapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnmNewTask -> {
                //Send Other View
                val builder = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setEnterAnim(R.anim.from_left)
                    .setExitAnim(R.anim.to_right)
                    .setPopEnterAnim(R.anim.from_right)
                    .setPopExitAnim(R.anim.to_left)

                //Envio de información
                val bundle = Bundle()
                bundle.putString(
                    "nada",
                    "nada"
                )
                val opciones: NavOptions = builder.build()
                Navigation.findNavController(v).navigate(R.id.nav_newTaskFragment, bundle, opciones)
            }
        }
    }

}