package com.sliide.sliideuser.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sliide.sliideuser.SliideUserApplication
import com.sliide.sliideuser.adapters.UserLongPressListener
import com.sliide.sliideuser.adapters.UsersAdapter
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
    private lateinit var usersListAdapter: UsersAdapter

    override fun onAttach(context: Context) {
        SliideUserApplication.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {
        val manager = LinearLayoutManager(requireContext())
        binding.rvUsers.layoutManager = manager

        usersListAdapter = UsersAdapter(UserLongPressListener { userId ->
            showDeleteUserDialog()
            true
            //TODO Future call to the viewmodel
        })

        binding.rvUsers.adapter = usersListAdapter

        binding.fabAddUser.setOnClickListener {
            Toast.makeText(requireContext(), "Add user clicked!", Toast.LENGTH_LONG).show()
            //TODO Add user functionality
        }

        binding.srlContainer.setOnRefreshListener {
            viewModel.refreshUsersList()
        }
    }

    private fun showDeleteUserDialog() {
        TODO("Not yet implemented")
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
                    binding.pbLoading.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                }
                Resource.Status.SUCCESS -> {
                    hideLoading()
                }
            }
        }
    }

    private fun observeUsers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.apply {
                usersListAdapter.submitList(this)
            }
        }
    }

    private fun hideLoading() {
        binding.pbLoading.visibility = View.GONE
        binding.srlContainer.isRefreshing = false
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}