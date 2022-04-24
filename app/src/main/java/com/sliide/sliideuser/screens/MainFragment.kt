package com.sliide.sliideuser.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sliide.sliideuser.SliideUserApplication
import com.sliide.sliideuser.databinding.FragmentMainBinding
import com.sliide.sliideuser.utils.Resource
import com.sliide.sliideuser.viewmodels.MainViewModel
import com.sliide.sliideuser.viewmodels.MainViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!! //this is safe
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    //the viewModel should not be referenced before onViewCreated()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        SliideUserApplication.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeProgress()
        observeUsers()
    }

    private fun observeProgress() {
        viewModel.progress.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    //show progress
                }
                Resource.Status.ERROR -> {
                    //hide progress & show a network error message
                }
                Resource.Status.SUCCESS -> {
                    //hide progress
                }
            }
        }
    }

    private fun observeUsers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.apply {
                firstOrNull()?.let {
                    Toast.makeText(
                        requireContext(),
                        "The first user is ${it.name}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}