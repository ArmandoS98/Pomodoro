package com.techun.pomodoro.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.FragmentTasksBinding
import com.techun.pomodoro.domain.TaskItem
import com.techun.pomodoro.ui.view.adapters.AllTasksAdapter
import com.techun.pomodoro.ui.view.adapters.CompletedTaskAdapter
import com.techun.pomodoro.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: AllTasksAdapter
    lateinit var completedTaskadapter: CompletedTaskAdapter
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        taskViewModel.onCreate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnmNewTask.setOnClickListener(this)

        taskViewModel.taskModel.observe(viewLifecycleOwner) { tasks ->
            if (tasks.isEmpty()) {
//                binding.tvRanking.visibility = View.VISIBLE
            } else {
//                binding.tvRanking.visibility = View.GONE


//                val pendingTasks = tasks.map { it.toPendingTask() }

                val completedTask = mutableListOf<TaskItem>()
                val pendingTasks = mutableListOf<TaskItem>()
                tasks.forEach {
                    if (it.task_completed == 1) {
                        //Completed Task
                        completedTask.add(it)
                    } else {
                        //Not completed task
                        pendingTasks.add(it)
                    }
                }
                println("Tasks= $tasks")
                binding.tvTotalProyects.text = tasks.size.toString()
                AllTasksAdapter(context).setMenu(tasks)
                CompletedTaskAdapter(context).setMenu(tasks)

                //Metodo el recyclerview
                recyclerInit(pendingTasks, completedTask)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.nsvTasks.setOnScrollChangeListener { _, _, _, _, _ ->
                if (binding.nsvTasks.scrollY > 0) {
                    binding.btnmNewTask.hide()
                } else {
                    binding.btnmNewTask.show()
                }
            }
        }
    }

    private fun recyclerInit(temp: List<TaskItem>, completedTasts: List<TaskItem>) {
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

                val opciones: NavOptions = builder.build()
                Navigation.findNavController(v).navigate(R.id.nav_newTaskFragment, null, opciones)
            }
        }
    }

}