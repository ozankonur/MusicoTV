package com.ozan.musicotv.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ozan.musicotv.data.model.LocalMusicResponse
import com.ozan.musicotv.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozan.musicotv.data.model.LocalResult
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.zip.ZipEntry

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _musicResponse: MutableLiveData<List<LocalResult>> = MutableLiveData()

    val musicResponse: LiveData<List<LocalResult>>
        get() = _musicResponse

    val searchQuery = MutableLiveData("")

    fun getMusics() {
        viewModelScope.launch {
            repository.getQueryResult().onEach { musicResponse ->
                _musicResponse.value = musicResponse.localFeed.localResults
            }.launchIn(viewModelScope)
        }
    }
}