package com.ilkeryildirim.soccerleague.ui.screens.home.items.todaysMatches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.ItemFixtureMatchBinding
import com.ilkeryildirim.soccerleague.databinding.ItemTodaysMatchBinding


class TodaysMatchItemAdapter(var matches: List<Match>, var teams: List<Team?>, val itemClick: (Match) -> Unit) : RecyclerView.Adapter<TodaysMatchItemAdapter.ViewHolder>() {
    private var lastPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTodaysMatchBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todays_match, parent, false)
        context = parent.context
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        matches[position].let { match ->
            holder.bind(match, teams)
            setAnimation(holder.itemView, position)
        }

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.itemView.clearAnimation()
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    class ViewHolder(private val binding: ItemTodaysMatchBinding, private val itemClick: (Match) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = TodaysMatchesViewModel()
        fun bind(match: Match, teams: List<Team?>) {
            binding.viewModel = viewModel
            viewModel.bind(match, teams)
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
