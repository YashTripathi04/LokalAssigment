package com.example.lokalassignment.ui.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.lokalassignment.R
import com.example.lokalassignment.databinding.JobItemBinding
import com.example.lokalassignment.model.BookmarkedJob
import com.example.lokalassignment.model.Result

class BookmarkAdapter(
    private val bookmarkListener: (BookmarkedJob, Boolean) -> Unit,
    private val clickListener: (BookmarkedJob) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    private var jobs: List<BookmarkedJob> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = JobItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val job = jobs[position]
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobs.size

    fun submitList(newJobs: List<BookmarkedJob>) {
        jobs = newJobs
        notifyDataSetChanged()
    }

    inner class BookmarkViewHolder(private val binding: JobItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: BookmarkedJob) {

            binding.apply {
                title.text = job.jobRole
                location.text = job.place
                salary.text = job.salary
                bookmarkSwitch.setImageResource(R.drawable.ic_bookmark_filled)

                bookmarkSwitch.setOnClickListener {
                    bookmarkListener(job, false)
                }

                btnCall.setOnClickListener {
                    val i = Intent(Intent.ACTION_DIAL)
                    i.data = Uri.parse(job.phone)
                    try {
                        startActivity(binding.root.context, i, null)
                    }catch (e: Exception){
                        Log.e("CallError", "bind: $e")
                        Toast.makeText(binding.root.context, "Number not available", Toast.LENGTH_SHORT).show()
                    }
                }

                btnWhatsapp.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(job.whatsapp)
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
}
