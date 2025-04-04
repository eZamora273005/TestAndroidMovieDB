package com.ezamora.testandroid.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezamora.testandroid.data.db.popular_movie.PopularMovie
import com.ezamora.testandroid.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ProfileViewModel by viewModels()

    private lateinit var adapter : ProfileAdapter

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
                handleSuccess(movies)
            }.onFailure { error ->
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun handleSuccess(movies: List<PopularMovie>) {
        adapter.moviesList = movies
        adapter.notifyDataSetChanged()
    }

    private fun initView() = with(binding) {
        rvPopularMovies.layoutManager = LinearLayoutManager(context)
        adapter = ProfileAdapter()
        binding.rvPopularMovies.adapter = adapter
    }

    private fun fetchMovies() {
        viewModel.getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}