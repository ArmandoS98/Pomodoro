package com.techun.pomodoro.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.techun.pomodoro.R
import com.techun.pomodoro.databinding.FragmentProfileBinding
import com.techun.pomodoro.databinding.FragmentTasksBinding

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgSettings.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgSettings->{
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
                Navigation.findNavController(v).navigate(R.id.nav_settingsFragment, bundle, opciones)
            }
        }
    }
}