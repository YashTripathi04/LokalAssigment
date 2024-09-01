package com.example.lokalassignment.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lokalassignment.databinding.FragmentJobsBinding
import com.example.lokalassignment.db.BookmarkedJobDatabase
import com.example.lokalassignment.model.BookmarkedJob
import com.example.lokalassignment.ui.adapter.JobPagingAdapter
import com.example.lokalassignment.ui.adapter.LoaderAdapter
import com.example.lokalassignment.repository.JobRepository
import com.example.lokalassignment.retrofit.JobApi
import com.example.lokalassignment.retrofit.RetrofitHelper
import com.example.lokalassignment.viewmodel.SharedViewModel
import com.example.lokalassignment.viewmodel.SharedViewModelFactory


class JobsFragment : Fragment() {

    private lateinit var binding: FragmentJobsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jobApi = RetrofitHelper.getInstance().create(JobApi::class.java)
        val jobDao = BookmarkedJobDatabase.getDatabase(requireContext()).bookmarkedJobDao()
        val repository = JobRepository(jobApi, jobDao)
        val sharedViewModel = ViewModelProvider(this, SharedViewModelFactory(repository)).get(SharedViewModel::class.java)

        val rvAdapter = JobPagingAdapter (
            bookmarkListener = { job, isBookmarked ->
                if (isBookmarked) {
                    val bookmarkedJob = BookmarkedJob(
                        jobId = job.id,
                        title = job.title,
                        place = job.primary_details.Place,
                        salary = job.primary_details.Salary,
                        phone = job.custom_link ?: "tel:${job.whatsapp_no}",
                        openings = job.openings_count.toString(),
                        qualifications = job.primary_details.Qualification,
                        experience = job.primary_details.Experience,
                        noOfApplications = job.num_applications.toString(),
                        views = job.views.toString(),
                        companyName = job.company_name,
                        jobDescription = job.other_details,
                        whatsapp = job.contact_preference.whatsapp_link,
                        jobRole = job.job_role
                    )
                    sharedViewModel.bookmarkJob(bookmarkedJob)

                } else {
                    sharedViewModel.unBookmarkJob(job.id)
                }
            },
            clickListener = { job ->
                val action = JobsFragmentDirections.actionJobsFragmentToJobDetailsFragment(job, null)
                findNavController().navigate(action)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = rvAdapter.withLoadStateFooter(
                footer = LoaderAdapter()
            )
        }
        sharedViewModel.jobList.observe(viewLifecycleOwner){
            rvAdapter.submitData(lifecycle, it)
        }

        rvAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.visibility = if (loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                tvLoading.visibility = if (loadState.source.refresh is LoadState.Loading) View.VISIBLE else View.GONE
                if (loadState.source.refresh is LoadState.Error) {
                    val errorState = loadState.source.refresh as LoadState.Error
                    tvLoading.visibility = View.VISIBLE
                    tvLoading.text = "Opps! Something went wrong."
                    Log.e("JobsFragment", "Error: ${errorState.error}")
                }
            }
        }
    }

}