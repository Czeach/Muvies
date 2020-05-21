package com.example.muvies.screens.featured

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.muvies.MainActivity

import com.example.muvies.R
import com.example.muvies.adapters.PopularListAdapter
import com.example.muvies.adapters.UpcomingListAdapter
import com.example.muvies.databinding.FeaturedFragmentBinding
import kotlinx.android.synthetic.main.featured_fragment.*

class FeaturedFragment : Fragment() {

    companion object {
        fun newInstance() = FeaturedFragment()
    }

    private lateinit var viewModel: FeaturedViewModel

    private var upcomingAdapter =
        UpcomingListAdapter(arrayListOf())

    private var popularAdapter =
        PopularListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        (requireActivity() as MainActivity).title = "Muvies"

        val binding = FeaturedFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(FeaturedViewModel::class.java)
        binding.lifecycleOwner = this
        binding.featuredViewModel = viewModel

        binding.apply {
            popularListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = popularAdapter
            }
            upcomingListRecycler.apply {
                layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
                adapter = upcomingAdapter
            }
        }

        viewModel.apply {
            upcomingLiveData.observe(viewLifecycleOwner, Observer {
                upcomingAdapter.updateUpcomingList(it)
            })
            popularLiveData.observe(viewLifecycleOwner, Observer {
                popularAdapter.updatePopularList(it)
            })
            topReatedLiveData.observe(viewLifecycleOwner, Observer {
                test.text = it.toString()
            })
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).title = "Muvies"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(FeaturedViewModel::class.java)

    }

}
