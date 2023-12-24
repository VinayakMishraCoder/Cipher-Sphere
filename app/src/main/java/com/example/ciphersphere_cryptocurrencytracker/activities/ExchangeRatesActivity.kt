package com.example.ciphersphere_cryptocurrencytracker.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ciphersphere_cryptocurrencytracker.R
import com.example.ciphersphere_cryptocurrencytracker.adapters.ExchangeRatesAdapter
import com.example.ciphersphere_cryptocurrencytracker.databinding.ActivityExchangeRatesBinding
import com.example.ciphersphere_cryptocurrencytracker.datamodels.ResultWrapper
import com.example.ciphersphere_cryptocurrencytracker.utils.OnClickUtils.uniClick
import com.example.ciphersphere_cryptocurrencytracker.utils.OnRefreshUtils
import com.example.ciphersphere_cryptocurrencytracker.viewmodels.ExchangeRatesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExchangeRatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExchangeRatesBinding
    private val viewModel: ExchangeRatesViewModel by viewModels()
    @Inject lateinit var currencyAdapter: ExchangeRatesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exchange_rates)
        supportActionBar?.hide()

        binding.recyclerView.adapter = currencyAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.retrieveCryptoData(false)
        }

        binding.retryButton.uniClick(true) {
            binding.retryButton.visibility = View.GONE
            viewModel.retrieveCryptoData(true)
        }

        observeData()

        startRefreshing()
    }

    private fun startRefreshing() {
        GlobalScope.launch {
            while (true) {
                delay(OnRefreshUtils.REFRESHING_INTERVAL)
                viewModel.retrieveCryptoData(true)
            }
        }
    }

    private fun observeData() {
        viewModel.responseData.observe(this) { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    currencyAdapter.setDataList(result.data)
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.retryButton.visibility = View.GONE
                    binding.loadingError.visibility = View.GONE
                    /**
                     * Set retry count back in-case the success is result of automatic-retry mechanism.
                     * */
                    viewModel.retryCount = OnRefreshUtils.RETRY_COUNT
                }
                is ResultWrapper.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this, "${result.message}", Toast.LENGTH_SHORT).show()
                    binding.loadingError.text = "Error Loading. Showing Older Results.\n${result.message}"
                    binding.loadingError.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.VISIBLE

                    /**
                     * Automatic retry mechanism, which will try once more after the request failed.
                     * */
                    if(viewModel.retryCount > 0) {
                        viewModel.retryCount--
                        Toast.makeText(this, "Re-trying once more.", Toast.LENGTH_SHORT).show()
                        viewModel.retrieveCryptoData(true)
                    }
                }
                is ResultWrapper.Loading -> {
                    binding.loadingError.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                }
            }
        }

        viewModel.refreshTime.observe(this) { time -> binding.refreshTime = time }
        viewModel.showLoader.observe(this) { showLoader -> binding.loading = showLoader }
    }
}