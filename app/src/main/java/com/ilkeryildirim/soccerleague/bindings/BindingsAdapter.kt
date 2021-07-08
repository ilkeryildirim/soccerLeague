package com.ilkeryildirim.soccerleague.bindings


import android.widget.ImageView
import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ilkeryildirim.soccerleague.R


@BindingAdapter("imageUrl")
fun setImageFromUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty().not()) {
        Glide.with(view.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade()).circleCrop()
                .placeholder(R.color.white)
                .into(view)
    }
}

