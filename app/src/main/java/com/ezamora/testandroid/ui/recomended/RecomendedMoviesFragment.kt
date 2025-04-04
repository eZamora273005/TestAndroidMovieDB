package com.ezamora.testandroid.ui.recomended

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezamora.testandroid.data.db.recomended_movie.RecomendedMovie
import com.ezamora.testandroid.databinding.FragmentRecomendedMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecomendedMoviesFragment : Fragment() {

    private var _binding: FragmentRecomendedMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : RecomendedViewModel by viewModels()
    private lateinit var adapter: RecomendedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecomendedMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        fetchMovies()
        observeViewModel()
    }

    private fun initView() = with(binding) {
        rvRecomended.layoutManager = LinearLayoutManager(context)
        adapter = RecomendedAdapter()
        rvRecomended.adapter = adapter
    }

    private fun fetchMovies() {
        viewModel.getRecomendedMovies()
    }

    private fun observeViewModel() {
        viewModel.topRated.observe(this@RecomendedMoviesFragment.viewLifecycleOwner) { result ->
            result.onSuccess { recomended ->
                handleSuccess(recomended)
            }.onFailure { error ->
                handleError(error)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun handleSuccess(recomended: List<RecomendedMovie>) {
        adapter.recomendedList = recomended
        adapter.notifyDataSetChanged()
    }

    private fun handleError(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}