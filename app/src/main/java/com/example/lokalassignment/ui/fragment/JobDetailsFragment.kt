package com.example.lokalassignment.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lokalassignment.R
import com.example.lokalassignment.databinding.FragmentJobDetailsBinding
import com.example.lokalassignment.model.BookmarkedJob
import com.example.lokalassignment.model.Result


class JobDetailsFragment : Fragment() {

    private lateinit var binding: FragmentJobDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJobDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            arguments?.let {

                val job = JobDetailsFragmentArgs.fromBundle(it).job
                if (job != null) {
                    populateJobDetails(job)
                } else {
                    val bookmarkedJob = JobDetailsFragmentArgs.fromBundle(it).bookmarkJob
                    if (bookmarkedJob != null) {
                        populateBookmarkedJobDetails(bookmarkedJob)
                    }
                }

                btnBack.setOnClickListener {
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun populateJobDetails(job: Result) {
        binding.apply {
            title.text = job.job_role
            location.text = job.primary_details.Place
            salary.text = job.primary_details.Salary
            companyName.text = job.company_name
            tvAboutJob.text = job.other_details
            val bookmarkIcon = if (job.is_bookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
            bookmarkSwitch.setImageResource(bookmarkIcon)

            bookmarkSwitch.setOnClickListener {
                val isBookmarked = !job.is_bookmarked
                job.is_bookmarked = isBookmarked

                val newBookmarkIcon = if (isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                bookmarkSwitch.setImageResource(newBookmarkIcon)

            }

            btnCall.setOnClickListener {
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse(job.custom_link)
                startActivity(i)
            }

            btnWhatsapp.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(job.contact_preference.whatsapp_link)
                startActivity(i)
            }
        }
    }

    private fun populateBookmarkedJobDetails(job: BookmarkedJob) {
        binding.apply {
            title.text = job.jobRole
            location.text = job.place
            salary.text = job.salary
            companyName.text = job.companyName
            tvAboutJob.text = job.jobDescription
            bookmarkSwitch.setImageResource(R.drawable.ic_bookmark_filled)

            bookmarkSwitch.setOnClickListener {
                if(bookmarkSwitch.resources.equals(R.drawable.ic_bookmark_filled)){
                    bookmarkSwitch.setImageResource(R.drawable.ic_bookmark)
                }
                else{
                    bookmarkSwitch.setImageResource(R.drawable.ic_bookmark_filled)
                }
            }

            btnCall.setOnClickListener {
                val i = Intent(Intent.ACTION_DIAL)
                i.data = Uri.parse(job.phone)
                startActivity(i)
            }

            btnWhatsapp.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(job.whatsapp)
                startActivity(i)
            }
        }
    }
}