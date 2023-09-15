package taha.younis.basicbankingapp.repositoy

import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.data.History
import taha.younis.basicbankingapp.db.BankDAO
import javax.inject.Inject

class BankRepository @Inject constructor(
    private val bankDAO: BankDAO
) {
    suspend fun getAllCustomers(): List<Customer>{
        return bankDAO.getAllCustomer()
    }
    suspend fun insertCustomer(customer: Customer){
        bankDAO.insertCustomer(customer)
    }
    suspend fun updateCustomer(customer: Customer){
        bankDAO.updateCustomer(customer)
    }
    suspend fun getAllHistory(): List<History>{
        return bankDAO.getAllHistories()
    }
    suspend fun insertHistory(history: History){
        bankDAO.insertHistory(history)
    }
}