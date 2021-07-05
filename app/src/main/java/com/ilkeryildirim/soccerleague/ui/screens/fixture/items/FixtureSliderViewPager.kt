package com.ilkeryildirim.soccerleague.ui.screens.fixture.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.ilkeryildirim.soccerleague.data.remote.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.ItemFixtureSliderBinding

class FixtureSliderViewPager (var context: Context, var featureds: List<Team>) : PagerAdapter() {

    private var _binding: ItemFixtureSliderBinding? = null
    private val binding get() = _binding!!
    val viewModel = FixtureSliderItemViewModel()
    override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return featureds.size
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        _binding = ItemFixtureSliderBinding.inflate(LayoutInflater.from(context), container, false)
        binding.viewModel = viewModel
        viewModel.bind(featureds[position])
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }
}