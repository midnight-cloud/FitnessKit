package com.example.fitnesskit.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesskit.R
import com.example.fitnesskit.databinding.FragmentMainBinding
import com.example.fitnesskit.presentation.adapters.TrainingAdapter
import com.example.fitnesskit.presentation.appComponent
import com.example.fitnesskit.presentation.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

//    private val adapter by lazy { TrainingAdapter(requireContext()) }
    @Inject
    lateinit var adapter : TrainingAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initRV()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.data.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun initRV() {
        binding.rvTrainings.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTrainings.adapter = adapter
        adapter.onItemClickListener = {
            Snackbar.make(
                requireActivity().findViewById(R.id.main_fragment),
                "Clicked ${it.title}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}