package com.ilkeryildirim.soccerleague.ui.screens.home.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.remote.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.ItemHomeLeaderboardBinding


class LeaderBoardAdapter(var teams: List<Team>, val itemClick: (Team) -> Unit) : RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder>() {
    private var lastPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemHomeLeaderboardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_home_leaderboard, parent, false)
        context = parent.context
        return ViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        teams.get(position).let { team -> holder.bind(team) }
        setAnimation(holder.itemView, position)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.itemView.clearAnimation()
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
      return teams.size
    }

    class ViewHolder(private val binding: ItemHomeLeaderboardBinding, private val itemClick: (Team) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = LeaderBoardItemViewModel()
        fun bind(team: Team) {
            binding.viewModel = viewModel
            viewModel.bind(team,adapterPosition)
            binding.root.setOnClickListener { itemClick(team) }
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


