package taha.younis.basicbankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.data.History
import taha.younis.basicbankingapp.repositoy.BankRepository
import taha.younis.basicbankingapp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel() {

    private val _history = MutableStateFlow<Resource<List<History>>>(Resource.Unspecified())
    val history: StateFlow<Resource<List<History>>> = _history

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _history.emit(Resource.Loading())
        }
        viewModelScope.launch {
            val historyList = bankRepository.getAllHistory()
            _history.emit(Resource.Success(historyList))
        }
    }

    fun insertToHistory(history: History) {
        viewModelScope.launch {
            bankRepository.insertHistory(history)
            loadItems()
        }
    }
}