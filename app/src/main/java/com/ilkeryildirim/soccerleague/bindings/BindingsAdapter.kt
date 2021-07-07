package com.ilkeryildirim.soccerleague.bindings


import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("imageUrl")
fun setImageFromUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty().not()) {
        Glide.with(view.context)
                .load(imageUrl)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}

@BindingAdapter("circleImageUrl")
fun setCircleImageFromUrl(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty().not()) {
        Glide.with(view.context)
                .load(imageUrl)
                .centerCrop()
                .circleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
    }
}