package com.example.lokalassignment.ui.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.databinding.JobItemBinding
import com.example.lokalassignment.model.Result

class JobPagingAdapter(
    private val bookmarkListener: (Result, Boolean) -> Unit,
    private val clickListener: (Result) -> Unit
): PagingDataAdapter<Result, JobPagingAdapter.JobViewHolder>(JOB_ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = getItem(position)
        job?.let {holder.bind(job, bookmarkListener, clickListener)}
    }

    class JobViewHolder(private val binding: JobItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(job: Result, bookmarkListener: (Result, Boolean) -> Unit, clickListener: (Result) -> Unit){

            binding.apply {
                title.text = job.job_role
                location.text = job.primary_details.Place
                salary.text = job.primary_details.Salary

                val bookmarkIcon = if (job.is_bookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                bookmarkSwitch.setImageResource(bookmarkIcon)

                bookmarkSwitch.setOnClickListener {
                    val isBookmarked = !job.is_bookmarked
                    job.is_bookmarked = isBookmarked

                    val newBookmarkIcon =
                        if (isBookmarked) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark
                    bookmarkSwitch.setImageResource(newBookmarkIcon)

                    bookmarkListener(job, isBookmarked)
                }

                btnCall.setOnClickListener {
                    val i = Intent(Intent.ACTION_DIAL)
                    i.data = Uri.parse(job.custom_link)
                    try {
                        startActivity(binding.root.context, i, null)
                    }catch (e: Exception){
                        Log.e("CallError", "bind: $e", )
                        Toast.makeText(binding.root.context, "Number not available", Toast.LENGTH_SHORT).show()
                    }
                }

                btnWhatsapp.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(job.contact_preference.whatsapp_link)
                    try {
                        startActivity(binding.root.context, i, null)
                    }catch (e: Exception){
                        Log.e("WhatsAppError", "bind: $e", )
                        Toast.makeText(binding.root.context, "WhatsApp number not available", Toast.LENGTH_SHORT).show()
                    }
                }

                root.setOnClickListener {
                    clickListener(job)
                }
            }
        }

    }

    companion object{
        private val JOB_ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

}