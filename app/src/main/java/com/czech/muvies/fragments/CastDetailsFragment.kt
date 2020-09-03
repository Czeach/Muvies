package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.adapters.TabAdapter
import com.czech.muvies.databinding.CastDetailsFragmentBinding
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.CastDetailsViewModel
import com.czech.muvies.viewModels.CastDetailsViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.cast_details_fragment.*

class CastDetailsFragment : Fragment() {

    private lateinit var viewModel: CastDetailsViewModel
    private lateinit var binding: CastDetailsFragmentBinding

    private lateinit var tabAdapter: TabAdapter
    val args: CastDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = CastDetailsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, CastDetailsViewModelFactory(MoviesApiService.getService()))
            .get(CastDetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.castDetailsViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviePerson = CastDetailsFragmentArgs.fromBundle(requireArguments()).moviePersonArgs
        val showPerson = CastDetailsFragmentArgs.fromBundle(requireArguments()).showPersonArgs

        if (moviePerson != null) {

            moviePerson.id?.let { getDetails(it) }
        }

        if (showPerson != null) {

            showPerson.id?.let { getDetails(it) }
        }

        tabAdapter = TabAdapter(this, createTabs())
        view_pager.adapter = tabAdapter

        TabLayoutMediator(tab_layout, view_pager) {tab, position ->
            when(position) {
                0 -> tab.text = "Movies"
                1 -> tab.text = "Tv Shows"
            }
        }.attach()
    }

    private fun createTabs(): ArrayList<Fragment> {
        val fragments: ArrayList<Fragment> = arrayListOf()

        fragments.add(MoviesTabFragment().apply {

        })

        fragments.add(TvShowsTabFragment().apply {

        })

        return fragments
    }

    private fun getDetails(personId: Int) {
        viewModel.getCastDetails(personId).observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data.let { personDetails ->

                            if (personDetails != null) {

                                Glide.with(this)
                                    .load("$BASE_IMAGE_PATH${personDetails.profilePath}")
                                    .placeholder(R.drawable.person_placeholder)
                                    .error(R.drawable.person_placeholder)
                                    .into(cast_image)

                                name.text = personDetails.name

                                biography.text = personDetails.biography
                            }
                        }
                        bio.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        bio.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        bio.visibility = View.GONE
                    }
                }
            }
        })
    }

}