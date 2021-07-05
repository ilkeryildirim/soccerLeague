package com.ilkeryildirim.soccerleague.ui.screens.fixture.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.remote.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.remote.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams
import com.ilkeryildirim.soccerleague.databinding.ItemFixtureMatchBinding



class WeeklyMatchesItemAdapter(var week: Week, var teams: Teams, val itemClick: (Match) -> Unit) : RecyclerView.Adapter<WeeklyMatchesItemAdapter.ViewHolder>() {
    private var lastPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFixtureMatchBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_leaderboard, parent, false)
        context = parent.context
        return ViewHolder(binding,teams, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        week.matches?.get(position)?.let { match ->
            holder.bind(match,teams)
            setAnimation(holder.itemView, position)
        }

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.itemView.clearAnimation()
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        val matches = week.matches
        return matches?.size ?: 0
    }

    class ViewHolder(private val binding: ItemFixtureMatchBinding,private val teams: Teams, private val itemClick: (Match) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = WeeklyMatchesViewModel()
        fun bind(match: Match,teams: Teams) {
            binding.viewModel = viewModel
            viewModel.bind(match,teams)
            binding.root.setOnClickListener { itemClick(match) }
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, R.anim.nav_default_enter_anim)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}
