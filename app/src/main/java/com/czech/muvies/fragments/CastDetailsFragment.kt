package com.czech.muvies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.czech.muvies.BASE_IMAGE_PATH
import com.czech.muvies.R
import com.czech.muvies.databinding.CastDetailsFragmentBinding
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Status
import com.czech.muvies.viewModels.CastDetailsViewModel
import com.czech.muvies.viewModels.CastDetailsViewModelFactory
import kotlinx.android.synthetic.main.cast_details_fragment.*

class CastDetailsFragment : Fragment() {

    private lateinit var viewModel: CastDetailsViewModel
    private lateinit var binding: CastDetailsFragmentBinding

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
                        details.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        details.visibility = View.INVISIBLE
                    }
                    Status.ERROR -> {
                        details.visibility = View.GONE
                    }
                }
            }
        })
    }

}