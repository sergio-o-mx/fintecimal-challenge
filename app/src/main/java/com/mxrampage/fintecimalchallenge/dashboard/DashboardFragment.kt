package com.mxrampage.fintecimalchallenge.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.mxrampage.fintecimalchallenge.R
import com.mxrampage.fintecimalchallenge.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private lateinit var dashboardBinding: FragmentDashboardBinding
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val dashboardListAdapter = DashboardListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dashboardBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        dashboardBinding.lifecycleOwner = viewLifecycleOwner
        dashboardBinding.dashboardViewModel = dashboardViewModel
        setupViewsPendingProperties()
        setupUIManagerObserver()
        return dashboardBinding.root
    }

    private fun setupViewsPendingProperties() {
        dashboardListAdapter.onItemClick = { place ->
            dashboardBinding.root.findNavController()
                .navigate(DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(place.id))
        }
        dashboardBinding.recyclerPlacesList.adapter = dashboardListAdapter
        dashboardBinding.textWelcomeDisplay.text = buildSpannedString {
            bold { append("${resources.getString(R.string.greeting_text_bold)} ") }
            append(resources.getString(R.string.greeting_text_normal))
        }
    }

    private fun setupUIManagerObserver() {
        dashboardViewModel.response.observe(viewLifecycleOwner, {
            when (it) {
                DashboardUIStateManager.Loading -> dashboardBinding.progressBar.visibility =
                    View.VISIBLE
                is DashboardUIStateManager.Message -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is DashboardUIStateManager.Response -> {
                    var visitsCounter = 0
                    dashboardBinding.progressBar.visibility = View.GONE
                    dashboardListAdapter.placesList = it.places
                    for (place in it.places) {
                        if (place.isVisited) {
                            visitsCounter++
                        }
                    }
                    dashboardBinding.textDailyVisitsIndicator.text =
                        resources.getString(R.string.daily_visits_template, visitsCounter)
                }
                is DashboardUIStateManager.Error -> {
                    dashboardBinding.progressBar.visibility = View.GONE
                    it.exception.message?.let { exceptionMessage ->
                        Log.e("DashboardFragment", exceptionMessage)
                        Toast.makeText(requireContext(), exceptionMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
