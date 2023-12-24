package com.example.ciphersphere_cryptocurrencytracker.adapters

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.example.ciphersphere_cryptocurrencytracker.R
import com.squareup.picasso.Picasso

object BindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: AppCompatImageView, url:String?){
        if(url.isNullOrEmpty() == true) {
            view.setImageResource(R.drawable.baseline_monetization_on_24)
        } else {
            Picasso.get().load(url)
                .placeholder(R.drawable.baseline_monetization_on_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(view)
        }
    }

    @BindingAdapter("setVisibility")
    @JvmStatic
    fun visibility(view: View, visibilityStatus: Boolean){
        if(visibilityStatus) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}