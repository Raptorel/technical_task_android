package com.sliide.sliideuser.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sliide.sliideuser.SliideUserApplication
import com.sliide.sliideuser.databinding.FragmentMainBinding
import com.sliide.sliideuser.viewmodels.MainViewModel
import com.sliide.sliideuser.viewmodels.MainViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!! //this is safe
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    lateinit var viewModel: MainViewModel

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
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}