package com.ypp.basicsproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ypp.core.network.model.home.HomeRepository
import com.ypp.datastore.UserInfo
import com.ypp.domain.HomeConfig
import com.ypp.domain.HomeConfigUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
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
    val getHomeConfig: StateFlow<HomeUiState> = homeConfigUseCase()
        .map{homeConfig->
            HomeUiState.Success(homeConfig)
        }.catch {
            HomeUiState.LoadFailed
        }.stateIn(viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading)
    fun updateBanner(){
        viewModelScope.launch (){

            val job=launch (CoroutineExceptionHandler { coroutineContext, throwable ->
                throwable.printStackTrace()
            }){
                homeRepository.updateBanner()
            }

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
    data class Success(val homeConfig: HomeConfig ):HomeUiState{
    }
}