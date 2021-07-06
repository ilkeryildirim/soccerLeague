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
import com.ilkeryildirim.soccerleague.data.remote.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.remote.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.remote.model.team.Team
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams
import com.ilkeryildirim.soccerleague.databinding.FragmentPagerBinding
import com.ilkeryildirim.soccerleague.ui.screens.fixture.items.WeeklyMatchesItemAdapter


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class PagerFragment : Fragment() {

    private val viewModel: PagerFragmentViewModel by viewModels()
    private var _binding: FragmentPagerBinding? = null
    private val binding get() = _binding!!
    var weeklyMatchesAdapter: WeeklyMatchesItemAdapter? = null

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
        getFixture()
    }

    private fun observeViewModel() {}

    private fun getFixture() {
        lifecycleScope.launchWhenCreated {
            var week: Week? = null
            var teams: Teams? = null
            week = arguments?.get("Week") as Week?
            teams = arguments?.get("Teams") as Teams?

            initWeekFixture(week!!, teams!!)
        }

    }

    private fun observeFragmentViewState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is PagerFragmentUIState.Initial -> {
                        hideContent()
                    }
                    is PagerFragmentUIState.Error -> {
                        state.message.let(::showError)
                    }
                    is PagerFragmentUIState.Loading -> {
                        hideContent()
                    }
                    else -> Unit
                }
            }
        }
    }


    private fun initWeekFixture(week: Week,teams: Teams) {
        binding.rvWeeklyMatches.apply {
            weeklyMatchesAdapter?.let { viewAdapter ->
                println("++ $week")
                println("++ $teams")
                viewAdapter.week = week
                viewAdapter.teams = teams
                viewAdapter.notifyDataSetChanged()
            }.run {
                weeklyMatchesAdapter = WeeklyMatchesItemAdapter(week,teams) {}
            }
            adapter = weeklyMatchesAdapter!!
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
