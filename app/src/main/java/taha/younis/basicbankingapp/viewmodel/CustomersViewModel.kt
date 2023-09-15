package taha.younis.basicbankingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.repositoy.BankRepository
import taha.younis.basicbankingapp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val bankRepository: BankRepository
) : ViewModel() {

    private val _allCustomers = MutableStateFlow<Resource<List<Customer>>>(Resource.Unspecified())
    val allCustomers: StateFlow<Resource<List<Customer>>> = _allCustomers

    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _allCustomers.emit(Resource.Loading())
        }
        viewModelScope.launch {
            val customerList = bankRepository.getAllCustomers()
            _allCustomers.emit(Resource.Success(customerList))
        }
    }

    fun insertCustomer(customer: Customer) {
        viewModelScope.launch {
            bankRepository.insertCustomer(customer)
            loadItems()
        }
    }

    fun updateSender(sender: Customer, money: Int) {
        viewModelScope.launch {
            sender.balance -= money
            bankRepository.updateCustomer(sender)
            val updatedList = allCustomers.value.data?.toMutableList()
            val index = updatedList?.indexOfFirst { it.phone == sender.phone }
            if (index != null && index != -1) {
                updatedList[index] = sender
                _allCustomers.emit(Resource.Success(updatedList))
            }
        }
    }
    fun updateReceiver(receiver: Customer, money: Int) {
        viewModelScope.launch {
            receiver.balance += money
            bankRepository.updateCustomer(receiver)
            val updatedList = allCustomers.value.data?.toMutableList()
            val index = updatedList?.indexOfFirst { it.phone == receiver.phone }
            if (index != null && index != -1) {
                updatedList[index] = receiver
                _allCustomers.emit(Resource.Success(updatedList))
            }
        }
    }
    fun updateCustomerBalance(sender: Customer, receiver: Customer, money: Int){
        viewModelScope.launch {
            receiver.balance += money
            sender.balance -= money
            bankRepository.updateCustomer(receiver) // for customer that u will send money for him
            bankRepository.updateCustomer(sender) /// for customer that u will take money from him
//            val updatedList = allCustomers.value.data?.toMutableList()
//            val index = updatedList?.indexOfFirst { it.phone == sender.phone }
//            if (index != null && index != -1) {
//                updatedList[index] = sender
//                _allCustomers.emit(Resource.Success(updatedList))
//            }
        }
    }
}