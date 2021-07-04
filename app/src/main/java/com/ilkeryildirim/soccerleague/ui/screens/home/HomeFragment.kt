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
import com.ilkeryildirim.soccerleague.databinding.FragmentHomeBinding


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


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
        getTeams()
        observeFragmentViewState()
        observeViewModel()
    }

    private fun observeViewModel() {}

    private fun observeFragmentViewState() {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is HomeFragmentUIState.Initial ->{
                        hideContent()
                    }
                    is HomeFragmentUIState.TeamsLoaded -> {
                        println("TEAM 1 ${state.discoverData.teams}")
                    }
                    is HomeFragmentUIState.FixtureLoaded -> {
                        println("TEAM 2 ${state.discoverData2.teams}")
                    }
                    is HomeFragmentUIState.Error -> {
                        state.message.let(::showError)
                        //or show error page
                    }
                    is HomeFragmentUIState.Loading -> {
                        hideContent()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun getTeams(){
        lifecycleScope.launchWhenCreated {
            viewModel.getTeamsAndFixture()
        }
    }

    private fun showContent(){
     //   binding.lytContent.animate().alpha(1.0f)
    }
    private fun hideContent(){
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
