package com.example.ciphersphere_cryptocurrencytracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ciphersphere_cryptocurrencytracker.databinding.ExchangeRateItemBinding
import com.example.ciphersphere_cryptocurrencytracker.datamodels.FinalCurrencyData
import javax.inject.Inject

class ExchangeRatesAdapter @Inject constructor(): RecyclerView.Adapter<ExchangeRatesAdapter.ExchangeRatesViewAdapter>() {
    private var dataList = ArrayList<FinalCurrencyData>()

    fun setDataList(listings: ArrayList<FinalCurrencyData>?) {
        if(listings != null) {
            this.dataList = listings
        }
        notifyDataSetChanged()
    }

    inner class ExchangeRatesViewAdapter(var binding: ExchangeRateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(currExchangeRate: FinalCurrencyData) {
                binding.currentExchangeRate = currExchangeRate
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRatesViewAdapter {
        return ExchangeRatesViewAdapter(ExchangeRateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExchangeRatesViewAdapter, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int  = dataList.size
}