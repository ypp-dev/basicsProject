package com.ypp.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ypp.data.repository.HomeRepository
import com.ypp.datastore.UserInfo
import com.ypp.domain.HomeConfig
import com.ypp.domain.HomeConfigUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    homeConfigUseCase: HomeConfigUseCase,
    private val homeRepository: HomeRepository
) :ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = homeConfigUseCase()
        .map{homeConfig->
            HomeUiState.Success(homeConfig)
        }.catch {
            HomeUiState.LoadFailed
        }.stateIn(viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading)
    fun updateBanner(){
        viewModelScope.launch (){
            async {
                homeRepository.updateBanner()
            }.await()
            Log.e("TAG", "updateBanner: "+  async {
                homeRepository.updateBanner()
            }.await() )

        }
    }
    fun addUserInfo(userInfo: UserInfo){
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.addUserInfo(userInfo)
        }
    }
    fun updateTopJson(){
        viewModelScope.launch {
            homeRepository.updateTopJson({

            },{

            })
        }
    }
}
sealed interface HomeUiState{
    data object Loading:HomeUiState
    data object EmptyQuery:HomeUiState
    data object LoadFailed:HomeUiState
    data class Success(val homeConfig: HomeConfig):HomeUiState{
    }
}