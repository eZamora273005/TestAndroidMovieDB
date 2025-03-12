package com.ezamora.testandroid.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezamora.testandroid.R
import com.ezamora.testandroid.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel : ProfileViewModel by viewModels()

    private lateinit var adapter : ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProfileBinding.bind(view)

        initView()
        fetchMovies()
        observer()
    }

    private fun observer() {
        viewModel.movies.observe(this@ProfileFragment.viewLifecycleOwner) { result ->
            result.onSuccess { movies ->
                adapter.moviesList = movies
                binding.rvPopularMovies.adapter = adapter

            }.onFailure { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun initView() = with(binding) {
        rvPopularMovies.layoutManager = LinearLayoutManager(context)
        adapter = ProfileAdapter()
    }


    private fun fetchMovies() {
        viewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}