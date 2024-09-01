package com.example.lokalassignment.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.lokalassignment.model.Result
import com.example.lokalassignment.retrofit.JobApi

class JobPagingSource(val jobApi: JobApi): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {

        return try {
            val position = params.key ?: 1
            val response = jobApi.getJobs(position)
            val jobs = response.results.filter{
                it.type != 1040
            }
            LoadResult.Page(
                data = jobs,
                prevKey = if (position == 1) null else position - 1,
                // as api has only 3 pages
                nextKey = if (position == 3) null else position + 1
            )
        }
        catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}