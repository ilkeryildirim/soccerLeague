package com.ilkeryildirim.soccerleague.ui.screens.scores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ilkeryildirim.soccerleague.data.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.FragmentScoresBinding
import com.ilkeryildirim.soccerleague.ui.screens.scores.ScoresFragmentUIState.*
import com.ilkeryildirim.soccerleague.ui.screens.scores.items.ScoresItemAdapter


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.Error


@AndroidEntryPoint
class ScoresFragment : Fragment() {

    private val viewModel: ScoresViewModel by viewModels()
    private var _binding: FragmentScoresBinding? = null
    private val binding get() = _binding!!
    var scoresIteamAdapter: ScoresItemAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoresBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeFragmentViewState()
        observeViewModel()

    }

    private fun observeViewModel() {}

    private fun observeFragmentViewState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is Initial, Loading -> {
                        //show loading etc.
                    }
                    is Error -> {
                        state.message?.let(::showError)
                    }
                    is TeamsLoaded -> {
                        with(state.teams.let(::getSortedTeamListByPoint)){
                            createScoreTable(this)
                        }
                    }
                }
            }
        }
    }

    private fun getSortedTeamListByPoint(teams:List<Team?>): List<Team?> {
       return teams.sortedByDescending { team -> team?.league_score?.toInt() }
    }

    private fun createScoreTable(teams: List<Team?>) {
        binding.rvScores.apply {
            scoresIteamAdapter?.let { viewAdapter ->
                viewAdapter.teams = teams
                viewAdapter.notifyDataSetChanged()
            }.run {
                scoresIteamAdapter = ScoresItemAdapter(teams)
            }
            adapter = scoresIteamAdapter!!
        }
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
    }

    private fun navigate(destinationId: Int, bundle: Bundle?) {
        findNavController().navigate(
                destinationId,
                bundle
        )
    }
}
