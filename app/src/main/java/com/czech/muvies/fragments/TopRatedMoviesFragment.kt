package com.czech.muvies.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.MainActivity
import com.czech.muvies.R
import com.czech.muvies.databinding.TopRatedMoviesFragmentBinding
import com.czech.muvies.models.Movies
import com.czech.muvies.pagedAdapters.TopRatedMoviesMainAdapter
import com.czech.muvies.pagedAdapters.topRatedItemClickListener
import com.czech.muvies.viewModels.TopRatedMoviesViewModel
//import koleton.api.hideSkeleton
//import koleton.api.loadSkeleton

class TopRatedMoviesFragment : Fragment() {

    private lateinit var viewModel: TopRatedMoviesViewModel
    private lateinit var binding: TopRatedMoviesFragmentBinding

    private val topRatedClickListener by lazy {
        object : topRatedItemClickListener {
            override fun invoke(it: Movies.MoviesResult) {
                val args = TopRatedMoviesFragmentDirections.actionTopRatedMoviesFragmentToDetailsFragment(
                    null, null, null, null, null, null,
                    it, null, null, null, null
                )
                findNavController().navigate(args)
            }

        }
    }
    private val topRatedAdapter = TopRatedMoviesMainAdapter(topRatedClickListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TopRatedMoviesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TopRatedMoviesViewModel::class.java)
        binding.topRatedMoviesVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topRatedMoviesMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = topRatedAdapter

//            loadSkeleton(R.layout.paged_list) {
//
//                color(R.color.colorSkeleton)
//                shimmer(true)
//            }
        }

        viewModel.getTopRatedList().observe(viewLifecycleOwner, Observer {

//            Handler().postDelayed({
//
//                binding.topRatedMoviesMainList.hideSkeleton()
//
//            }, 2000)

            topRatedAdapter.submitList(it)
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).showBottomNavigation()
    }

}