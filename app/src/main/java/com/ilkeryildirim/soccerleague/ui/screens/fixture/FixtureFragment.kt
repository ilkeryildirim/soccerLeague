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
import com.ilkeryildirim.soccerleague.data.remote.model.fixture.Fixture
import com.ilkeryildirim.soccerleague.data.remote.model.team.Teams
import com.ilkeryildirim.soccerleague.databinding.FragmentFixtureBinding
import com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment.PagerFragment
import com.ilkeryildirim.soccerleague.ui.screens.fixture.pagerFragment.ViewPagerAdapter
import com.orhanobut.hawk.Hawk


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class FixtureFragment : Fragment() {

    private val viewModel: FixtureViewModel by viewModels()
    private var _binding: FragmentFixtureBinding? = null
    private val binding get() = _binding!!
    var fixtureAdapter: ViewPagerAdapter? = null

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
        createFixture()
        observeFragmentViewState()
        observeViewModel()

    }

    private fun observeViewModel() {}

    fun createFixture() {
        fixtureAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        lifecycleScope.launchWhenCreated {
            var fixture: Fixture? = null
            fixture = Hawk.get<Fixture>("Fixture")

            fixture?.week?.forEach {

                fixtureAdapter!!.addFragment(PagerFragment())
            }
        }
        binding.viewPager.adapter = fixtureAdapter
    }


private fun observeFragmentViewState() {
    lifecycleScope.launchWhenCreated {
        viewModel.uiState.collectLatest { state ->
            when (state) {
                is FixtureFragmentUIState.Initial -> {
                    hideContent()
                }
                is FixtureFragmentUIState.Error -> {
                    state.message.let(::showError)
                }
                is FixtureFragmentUIState.Loading -> {
                    hideContent()
                }
                else -> Unit
            }
        }
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
