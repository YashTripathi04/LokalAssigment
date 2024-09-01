package com.example.lokalassignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokalassignment.databinding.FragmentBookmarkBinding
import com.example.lokalassignment.db.BookmarkedJobDatabase
import com.example.lokalassignment.repository.JobRepository
import com.example.lokalassignment.retrofit.JobApi
import com.example.lokalassignment.retrofit.RetrofitHelper
import com.example.lokalassignment.ui.adapter.BookmarkAdapter
import com.example.lokalassignment.viewmodel.SharedViewModel
import com.example.lokalassignment.viewmodel.SharedViewModelFactory

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAdapter = BookmarkAdapter (
            bookmarkListener = { job, isBookmarked ->
                if (isBookmarked) {
                    sharedViewModel.bookmarkJob(job)
                } else {
                    sharedViewModel.unBookmarkJob(job.jobId)
                }
            },
            clickListener = { bookmarkedJob ->
                val action = BookmarkFragmentDirections.actionBookmarkFragmentToJobDetailsFragment(null, bookmarkedJob)
                findNavController().navigate(action)
            }
        )
        if (rvAdapter.itemCount == 0){
            binding.tvNoBookmark.visibility = View.VISIBLE
        }else{
            binding.tvNoBookmark.visibility = View.GONE
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }

        val jobApi = RetrofitHelper.getInstance().create(JobApi::class.java)
        val jobDao = BookmarkedJobDatabase.getDatabase(requireContext()).bookmarkedJobDao()
        val repository = JobRepository(jobApi, jobDao)
        sharedViewModel = ViewModelProvider(this, SharedViewModelFactory(repository)).get(SharedViewModel::class.java)

        sharedViewModel.bookmarkedJobs.observe(viewLifecycleOwner) { jobs ->
            if (jobs.isEmpty()) binding.tvNoBookmark.visibility = View.VISIBLE
            else binding.tvNoBookmark.visibility = View.GONE
            rvAdapter.submitList(jobs)
        }

    }
}
