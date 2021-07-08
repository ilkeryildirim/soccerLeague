package com.ilkeryildirim.soccerleague.ui.screens.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.ilkeryildirim.soccerleague.data.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.model.fixture.Week
import com.ilkeryildirim.soccerleague.data.model.team.Teams
import com.ilkeryildirim.soccerleague.databinding.FragmentFixtureBinding
import com.ilkeryildirim.soccerleague.ui.screens.fixture.FixtureFragmentUIState.*
import com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment.PagerFragment
import com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment.ViewPagerAdapter
import com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment.ViewPagerTransformer

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class FixtureFragment : Fragment() {

    private val viewModel: FixtureViewModel by viewModels()
    private var _binding: FragmentFixtureBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFixtureBinding.inflate(inflater, container, false)
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
                    is FixtureLoaded -> {
                        state.fixture.let(::createWeeklyFixtures)
                    }
                }
            }
        }
    }

    private fun createWeeklyFixtures(weeks: List<Week?>) {
        val fixtureAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        weeks.forEachIndexed { index, week ->
            val bundle = Bundle()
            bundle.putInt(INDEX_WEEK, index)
            fixtureAdapter.addFragmentWithBundle(PagerFragment(), bundle)
        }
        with(binding) {
            viewPager.setPageTransformer(ViewPagerTransformer())
            viewPager.adapter = fixtureAdapter
            TabLayoutMediator(tabDots, viewPager) { tab, position -> }.attach()
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
