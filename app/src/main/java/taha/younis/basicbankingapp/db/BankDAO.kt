package taha.younis.basicbankingapp.db

import androidx.room.*
import kotlinx.coroutines.flow.StateFlow
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.data.History

@Dao
interface BankDAO {

    @Query("SELECT * FROM customer_table")
    suspend fun getAllCustomer(): List<Customer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Query("SELECT * FROM history_table")
    suspend fun getAllHistories(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

}