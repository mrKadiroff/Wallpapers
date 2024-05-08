package com.shoxruxbek.wallpapers.viewmodels

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shoxruxbek.wallpapers.models.Hit
import com.shoxruxbek.wallpapers.retrofit.RetrofitService

class UserDataSource(
    private val apiService: RetrofitService,
    private val photo: String,
    private val searchQuery: String
) : PagingSource<Int, Hit>() {

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        // Since we don't have a way to determine the key for refreshing,
        // returning null indicates that the PagingSource cannot currently support refreshing.
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        try {
            val nextPageNumber = params.key ?: 1
            val usersData = apiService.getListPhotos(
                "27240519-6e85e045b4edde1049de33f01",
                searchQuery,
                "photo",
                true,
                nextPageNumber,
                params.loadSize,
                true
            )
            return LoadResult.Page(usersData.hits, null, nextPageNumber + 1)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    // Rest of the code...
}
