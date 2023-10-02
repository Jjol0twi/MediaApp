package com.example.mediaapp.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaapp.data.model.channel.ChannelResponse
import com.example.mediaapp.data.model.video.SearchResponse
import com.example.mediaapp.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class SearchViewModel(
    private val searchRepository: VideoRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse>()
    private val _channelSearchResult = MutableLiveData<ChannelResponse>()
    private val _trendingSearchResult = MutableLiveData<SearchResponse>()

    val searchResult: LiveData<SearchResponse> get() = _searchResult
    val trendingResult : LiveData<SearchResponse> get() = _trendingSearchResult
    val channelResult : LiveData<ChannelResponse> get() = _channelSearchResult

    fun searchYoutube(videoCategoryId:String) = viewModelScope.launch(Dispatchers.IO){
        try {
            val response:Response<SearchResponse> = searchRepository.search(Constants.API_KEY,"snippet",videoCategoryId,"mostPopular","ko-KR",10,"KR")
            if (response.isSuccessful){
                response.body()?.let { body ->
                    _searchResult.postValue(body)

                }
            } else {
                val videosErrorBody: ResponseBody? = response.errorBody()
            }
        } catch (e: Exception) {
        }
    }

    fun searchTrending() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val trendingResponse:Response<SearchResponse> = searchRepository.search(Constants.API_KEY,"snippet","0","mostPopular","ko-KR",10,"KR")
            if (trendingResponse.isSuccessful){
                trendingResponse.body()?.let { body ->
                    _trendingSearchResult.postValue(body)
                }
            }else {
                val trendingErrorBody:ResponseBody? = trendingResponse.errorBody()
            }

        }catch (e: Exception){

        }
    }

    fun searchChannels(id:String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val channelResponse:Response<ChannelResponse> = searchRepository.searchChannel(Constants.API_KEY,"snippet",15,id,"KR","channel")
            if (channelResponse.isSuccessful){
                channelResponse.body()?.let { body ->
                    _channelSearchResult.postValue(body)
                }
            }else {
                val channelsErrorBody : ResponseBody? = channelResponse.errorBody()
            }
        }catch (e: Exception){

        }
    }
}