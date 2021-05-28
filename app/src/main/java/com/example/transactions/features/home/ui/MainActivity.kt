package com.example.transactions.features.home.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.transactions.R
import com.example.transactions.core.domain.RequestFailure
import com.example.transactions.core.extensions.doIfFailure
import com.example.transactions.core.extensions.doIfInProgress
import com.example.transactions.core.extensions.doIfSuccess
import com.example.transactions.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initUI()
    }

    private fun initUI() {
        setupObservers()
        initListeners()
    }

    private fun setupObservers() {
        homeViewModel.rates.observe(this) {
            it.doIfSuccess { items ->
                binding.tvRates.text = items.toString()
                homeViewModel.getTransactions()
            }

            it.doIfFailure {
                manageError(it)
            }

            it.doIfInProgress {
            }
        }

        homeViewModel.transactions.observe(this){
            it.doIfSuccess {
                homeViewModel.mapResults(it)
            }
            it.doIfFailure {
                manageError(it)
            }

            it.doIfInProgress {
            }
        }
    }

    private fun initListeners() {
        binding.btnSearch.setOnClickListener {
            homeViewModel.getRates()
        }
    }

    private fun manageError(requestFailure: RequestFailure) {
        val message = when (requestFailure) {
            is RequestFailure.ApiError -> requestFailure.message
            is RequestFailure.NoConnectionError -> getString(R.string.connection_error_message)
            is RequestFailure.UnknownError -> getString(R.string.default_error_message)
        }
        if (message.isNullOrEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}