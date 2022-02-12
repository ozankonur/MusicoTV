package com.ozan.musicotv.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozan.musicotv.data.network.entity.Result
import com.ozan.musicotv.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    private var tempMusicResponse: MutableMap<String, ArrayList<Result>> = mutableMapOf()
    private val _musicResponse: MutableLiveData<MutableMap<String, ArrayList<Result>>> =
        MutableLiveData()

    val musicResponse: LiveData<MutableMap<String, ArrayList<Result>>>
        get() = _musicResponse

    fun getMusics() {
        viewModelScope.launch {
            tempMusicResponse = mutableMapOf()
            repository.getQueryResult().onEach { musicResponse ->
                musicResponse.feed.results.onEach { result ->
                    result.genres.forEach { genre ->
                        if (genre.genreId != "34") {
                            if (tempMusicResponse[genre.name] != null) {
                                tempMusicResponse[genre.name]?.add(result)
                            } else {
                                tempMusicResponse[genre.name] = arrayListOf(result)
                            }
                        }
                    }
                }
                _musicResponse.value = tempMusicResponse
            }.launchIn(viewModelScope)
        }
    }

    fun search(keyword: String) {
        val tempFilteredMusicResponse: MutableMap<String, ArrayList<Result>> = mutableMapOf()
        for (item in tempMusicResponse) {
            val temp = item.value.filterIndexed { _, result ->
                (result.artistName.lowercase().contains(keyword.lowercase()) ||
                        result.name.lowercase().contains(keyword.lowercase()))
            }
            tempFilteredMusicResponse[item.key] = ArrayList(temp)
        }
        _musicResponse.value = mutableMapOf()
        _musicResponse.value = tempFilteredMusicResponse
    }
}