package com.ezamora.testandroid.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezamora.testandroid.R
import com.ezamora.testandroid.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MoviesViewModel by viewModels()

    private lateinit var adapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesBinding.bind(view)

        initView()
        fetchMovies()
        observer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    private fun observer() {
        viewModel.topRated.observe(this@MoviesFragment.viewLifecycleOwner) { result ->
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
        adapter = MoviesAdapter()
    }

    private fun fetchMovies() {
        viewModel.getMoviesTopRated()
    }
}