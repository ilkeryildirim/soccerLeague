package com.ilkeryildirim.soccerleague.ui.screens.scores.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilkeryildirim.soccerleague.R
import com.ilkeryildirim.soccerleague.data.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.ItemScoresBinding


class ScoresItemAdapter(var teams: List<Team?>) :
    RecyclerView.Adapter<ScoresItemAdapter.ViewHolder>() {
    private var lastPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemScoresBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_scores,
            parent,
            false
        )
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        teams[position].let { team ->
            team?.let { _team -> holder.bind(_team,position+1) }
            setAnimation(holder.itemView, position)
        }

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.itemView.clearAnimation()
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    class ViewHolder(private val binding: ItemScoresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ScoresItemViewModel()
        fun bind(team: Team,position: Int) {
            binding.viewModel = viewModel
            viewModel.bind(team,position)
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
