package com.example.themoviedb.ui.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.articals.ui.adapters.viewmodel.MainViewModelFactory
import com.example.themoviedb.R
import com.example.themoviedb.data.api.ApiHelper
import com.example.themoviedb.data.api.RetrofitBuilder
import com.example.themoviedb.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService),
                requireActivity().applicationContext
            )
        ).get(MainViewModel::class.java)
        initializeValues()

        viewModel.emailMessage.observe(getViewLifecycleOwner(), Observer {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    valid_email.visibility = View.GONE
                } else {
                    valid_email.visibility = View.VISIBLE
                    valid_email.text = it
                }
            }
        })
        viewModel.passwordMessage.observe(getViewLifecycleOwner(), Observer {
            it?.let {
                if (TextUtils.isEmpty(it)) {
                    valid_password.visibility = View.GONE
                } else {
                    valid_password.visibility = View.VISIBLE
                    valid_password.text = it
                }
            }
        })
        viewModel.addFragment.observe(getViewLifecycleOwner(), Observer {
            it?.let {
                if (it) {
                    valid_password.visibility = View.GONE
                    valid_email.visibility = View.GONE
                    requireActivity().currentFocus?.let { view ->
                        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    addMovieListFragment()
                }
            }
        })
    }

    private fun initializeValues() {

        loginButton.setOnClickListener {
            viewModel.validateValue(email.text.toString(), password.text.toString())


        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
        ) =
            LoginFragment().apply {

            }
    }

    fun addMovieListFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, MovieListFragment.newInstance()).commit()
    }
}