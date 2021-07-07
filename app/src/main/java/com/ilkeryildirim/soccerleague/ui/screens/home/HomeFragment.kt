package com.ilkeryildirim.soccerleague.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.fixture.Match
import com.ilkeryildirim.soccerleague.data.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.FragmentHomeBinding
import com.ilkeryildirim.soccerleague.ui.screens.home.items.leaderBoad.LeaderBoardAdapter
import com.ilkeryildirim.soccerleague.ui.screens.home.items.todaysMatches.TodaysMatchItemAdapter
import com.ilkeryildirim.soccerleague.util.DateUtil


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var leaderBoardAdapter: LeaderBoardAdapter? = null
    var todaysMatchesAdapter: TodaysMatchItemAdapter? = null
    private lateinit var teams: List<Team>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
                    is HomeFragmentUIState.Initial -> {
                        hideContent()
                    }
                    is HomeFragmentUIState.TeamsLoaded -> {
                        state.teams.teams?.let { allTeams ->
                            teams = allTeams
                        }
                        initLeaderBoardAdapter(getFilteredTeamsForLeaderBoard(teams))
                    }
                    is HomeFragmentUIState.FixtureLoaded -> {
                        initTodaysMatches(getfilteredMatchesForToday(state.fixture))
                    }
                    is HomeFragmentUIState.Error -> {
                        state.message.let(::showError)
                        //or show error page
                    }
                    is HomeFragmentUIState.Loading -> {
                        hideContent()
                    }
                    is HomeFragmentUIState.Navigate -> {
                        state.apply {
                            navigate(destinationId, bundle)
                        }
                    }
                }
            }
        }
    }

    private fun getfilteredMatchesForToday(fixture: Fixture): List<Match> {
        val todaysMatches = arrayListOf<Match>()
        fixture.week.forEach { week ->
            week?.matches?.forEach { match ->
                if (DateUtil().isInTodayRange(match.time)) todaysMatches.add(match)
            }
        }
        return todaysMatches
    }

    private fun getFilteredTeamsForLeaderBoard(allTeams: List<Team>): List<Team> {
        val filteredTeams: List<Team>
        allTeams.let { teamList ->
            filteredTeams = teamList.sortedByDescending { team ->
                team.league_score?.toInt()
            }.subList(0, 3)
        }
        return filteredTeams
    }

    private fun initLeaderBoardAdapter(list: List<Team>) {
        binding.rvLeaderBoard.apply {
            leaderBoardAdapter?.let { viewAdapter ->
                viewAdapter.teams = list
                viewAdapter.notifyDataSetChanged()
            }.run {
                leaderBoardAdapter = LeaderBoardAdapter(list) {}
            }
            adapter = leaderBoardAdapter
        }
    }

    private fun initTodaysMatches(matches: List<Match>) {
        binding.rvTodaysMatches.apply {
            todaysMatchesAdapter?.let { viewAdapter ->
                viewAdapter.matches = matches
                viewAdapter.teams = teams
                viewAdapter.notifyDataSetChanged()
            }.run {
                todaysMatchesAdapter = TodaysMatchItemAdapter(matches, teams) {}
            }
            adapter = todaysMatchesAdapter!!
        }
    }


    private fun showContent() {
        //   binding.lytContent.animate().alpha(1.0f)
    }

    private fun hideContent() {
        //   binding.lytContent.animate().alpha(0.0f)
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
