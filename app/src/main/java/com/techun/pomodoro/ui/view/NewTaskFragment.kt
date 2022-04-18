package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.FragmentNewTaskBinding
import com.techun.pomodoro.domain.TaskItem
import com.techun.pomodoro.ui.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTaskFragment : Fragment(), View.OnClickListener {
    private var selectedPriority: String? = "2"
    private var _binding: FragmentNewTaskBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Tasks
        val tasks = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, tasks)
        (binding.tilTask.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        //Work Interval
        val workInterval = listOf(15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30)
        val adapterWorkInterval = ArrayAdapter(requireContext(), R.layout.list_item, workInterval)
        (binding.tilWorkInterval.editText as? AutoCompleteTextView)?.setAdapter(adapterWorkInterval)

        //Short Break
        val adapterShortBreak = ArrayAdapter(requireContext(), R.layout.list_item, tasks)
        (binding.tilShortBreak.editText as? AutoCompleteTextView)?.setAdapter(adapterShortBreak)

        binding.imgBackArrow.setOnClickListener(this)
        binding.btnmCancel.setOnClickListener(this)
        binding.btnmSave.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgBackArrow, R.id.btnmCancel -> {
                findNavController().popBackStack(R.id.nav_tasks, false)
            }
            R.id.btnmSave -> {
                val name = binding.tieTaskName.text.toString()
                val description = binding.tieShortDescription.text.toString()
                val priority = when {
                    binding.cLow.isChecked -> {
                        2
                    }
                    binding.cMedium.isChecked -> {
                        1
                    }
                    else -> {
                        0
                    }
                }
                selectedPriority?.toInt()
                val noTasks = binding.actTasks.text.toString().toInt()
                val duration = binding.actWorkG.text.toString().toInt()
                val shortBreak = binding.actBreak.text.toString().toInt()

                binding.cgPriority.children.forEach {
                    (it as Chip).setOnCheckedChangeListener { _, _ ->
                        handleSelection()
                    }
                }

                taskViewModel.insertTaskModel.observe(viewLifecycleOwner) {
                    if (it != (-1).toLong()) {
                        println("Result Insert: $it")
                        findNavController().popBackStack(R.id.nav_tasks, false)
                    } else
                        println("Se produjo un error al momento de insertar en la DB")
                }

                taskViewModel.insertTask(
                    TaskItem(
                        name,
                        description,
                        priority,
                        noTasks,
                        duration,
                        shortBreak,
                        0,
                        0,
                        0
                    )
                )
            }
        }
    }

    private fun handleSelection() {
        binding.cgPriority.checkedChipIds.forEach {
            val chip = binding.root.findViewById<Chip>(it)
            println("values: ${chip.text}")
        }
    }
}