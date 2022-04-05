package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.techun.pomodoro.databinding.ChipBinding
import com.techun.pomodoro.databinding.FragmentNewTaskBinding


class NewTaskFragment : Fragment() {
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
        setupChip()
    }

    private fun setupChip() {
        for (i in 1..30) {
            val chip = createChip(i.toString())
            binding.cgTask.addView(chip)
        }
        for (i in 1..30) {
            val chip = createChip(i.toString())
            binding.cgWorkInterval.addView(chip)
        }
        for (i in 1..30) {
            val chip = createChip(i.toString())
            binding.cgShortBreak.addView(chip)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = ChipBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }

}