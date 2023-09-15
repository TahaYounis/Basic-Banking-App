package taha.younis.basicbankingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.data.History

@Database(entities = [Customer::class, History::class], version = 3)
abstract class BankDatabase : RoomDatabase() {

    abstract fun getBankDao(): BankDAO
//
//    companion object {
//        @Volatile
//        private var instance: BankDatabase? = null
//        private val LOCK = Any()
//
//        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//
//            instance ?: createDatabase(context).also { instance = it }
//        }
//
//        private fun createDatabase(context: Context) =
//            Room.databaseBuilder(
//                context.applicationContext,
//                BankDatabase::class.java,
//                "bank_db.db"
//            ).fallbackToDestructiveMigration().build()
//    }
}