package com.example.themoviedb.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.articals.ui.adapters.viewmodel.MainViewModelFactory
import com.example.themoviedb.R
import com.example.themoviedb.data.api.ApiHelper
import com.example.themoviedb.data.api.RetrofitBuilder
import com.example.themoviedb.data.model.ResponsePopularMovies
import com.example.themoviedb.data.model.Results
import com.example.themoviedb.ui.adapter.MovieListAdapter

import com.example.themoviedb.ui.viewmodel.MainViewModel
import com.example.themoviedb.utils.GenericUtil
import com.example.themoviedb.utils.Resource
import com.example.themoviedb.utils.Status
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

    }


    fun observeData() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService),
                requireActivity().applicationContext
            )
        ).get(MainViewModel::class.java)

        if (GenericUtil.isNetworkAvailable(requireActivity())) {
            fetchArticalList()

        } else {
            progress_circular.visibility = View.GONE
            message.text = "Please Check Your Intenet Connection!!"
            message.visibility = View.VISIBLE
        }

    }

    private fun fetchArticalList() {
        viewModel.getMovieList().observe(getViewLifecycleOwner(),
            Observer<Resource<ResponsePopularMovies>> {
                when (it.status) {
                    Status.SUCCESS -> {
                        progress_circular.visibility = View.GONE
                        gridview.visibility = View.VISIBLE

                        it.data?.let {
                            setupGridView(it.results)
                        }
                    }

                    Status.LOADING -> {
                        progress_circular.visibility = View.VISIBLE
                    }

                    Status.ERROR -> {
                        progress_circular.visibility = View.GONE
                        message.text = it.message
                        message.visibility = View.VISIBLE
                        gridview.visibility = View.GONE

                    }

                }
            })


    }



    private fun setupGridView(result: ArrayList<Results>) {
        val adapter = MovieListAdapter(requireActivity(), R.layout.item,result)
        gridview.adapter = adapter


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MovieListFragment().apply {

            }
    }
}