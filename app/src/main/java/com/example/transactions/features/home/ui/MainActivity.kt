package com.example.transactions.features.home.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.transactions.R
import com.example.transactions.core.domain.RequestFailure
import com.example.transactions.core.extensions.doIfFailure
import com.example.transactions.core.extensions.doIfInProgress
import com.example.transactions.core.extensions.doIfSuccess
import com.example.transactions.databinding.ActivityMainBinding
import com.example.transactions.features.home.domain.model.Transaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var dictionary: Dictionary

    private lateinit var binding: ActivityMainBinding

    private lateinit var transactionsList: List<Transaction>

    private lateinit var itemSelected: String

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
                dictionary.setUpDictionary(items)
                btnShow.visibility = View.VISIBLE
            }

            it.doIfFailure {
                manageError(it)
            }

            it.doIfInProgress {
                btnShow.visibility = View.GONE
            }
        }

        homeViewModel.transactions.observe(this) {
            it.doIfSuccess {
                setItemsToSpinner(homeViewModel.getItems(it))
                transactionsList = it
                homeViewModel.getRates()
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
            homeViewModel.getTransactions()
            btnSearch.visibility = View.GONE
        }
        binding.btnShow.setOnClickListener {
            dictionary.initTails()
            binding.tvTransactions.text = dictionary.convertToEur(filterTransactions(itemSelected)).toString()
        }
    }

    private fun setItemsToSpinner(items: List<String>) {
        val spinner = binding.spinnerAccounts
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        arrayAdapter.addAll(items)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.onItemSelectedListener = this
        spinner.adapter = arrayAdapter
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        itemSelected = parent?.getItemAtPosition(position).toString()
    }

    fun filterTransactions(filter: String): List<Transaction> {
        return transactionsList.filter { it.sku == filter }
    }
}