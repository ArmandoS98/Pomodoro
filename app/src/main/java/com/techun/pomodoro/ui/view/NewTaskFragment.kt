package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.FragmentNewTaskBinding


class NewTaskFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentNewTaskBinding? = null
    private val binding get() = _binding!!
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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgSettings, R.id.btnmCancel -> {
                findNavController().popBackStack(R.id.nav_tasks, false)
            }
        }
    }

    /*  private fun setupChip() {
          for (i in 1..10) {
              val chip = createChip(i.toString())
              binding.cgTask.addView(chip)
          }
          for (i in 15..30) {
              val chip = createChip(i.toString())
              binding.cgWorkInterval.addView(chip)
          }
          for (i in 1..10) {
              val chip = createChip(i.toString())
              binding.cgShortBreak.addView(chip)
          }
      }

      private fun createChip(label: String): Chip {
          val chip = ChipBinding.inflate(layoutInflater).root
          chip.text = label
          return chip
      }*/

}