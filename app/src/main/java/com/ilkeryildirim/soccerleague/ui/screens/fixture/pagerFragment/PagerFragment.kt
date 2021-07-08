package com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment

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
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.model.team.Team
import com.ilkeryildirim.soccerleague.databinding.FragmentPagerBinding
import com.ilkeryildirim.soccerleague.ui.screens.fixture.items.FixtureMatchItemAdapter
import com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment.PagerFragmentUIState.*


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PagerFragment : Fragment() {

    private val viewModel: PagerFragmentViewModel by viewModels()
    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!
    var fixtureMatchAdapter: FixtureMatchItemAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagerBinding.inflate(inflater, container, false)
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
                        state.message.let(::showError)
                    }
                    is FixtureAndTeamsLoaded -> {
                        with(state) {
                            getWeekIndex()?.let { index ->
                                fixture[index]?.let { weeklyFixture ->
                                    binding.tvWeekDescription.text = weeklyFixture.description
                                    initWeekFixture(weeklyFixture, teams)
                                }
                            }.run {
                                //show an error occurred
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getWeekIndex(): Int? {
        return arguments?.getInt(INDEX_WEEK)
    }

    private fun initWeekFixture(week: Week, teams: List<Team?>) {
        binding.rvWeeklyMatches.apply {
            fixtureMatchAdapter?.let { viewAdapter ->
                viewAdapter.week = week
                viewAdapter.teams = teams
                viewAdapter.notifyDataSetChanged()
            }.run {
                fixtureMatchAdapter = FixtureMatchItemAdapter(week, teams) {}
            }
            adapter = fixtureMatchAdapter!!
        }
    }


    override fun onDestroy() {
        _binding = null
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

    companion object {
        private const val INDEX_WEEK = "Week_Index"
    }
}
