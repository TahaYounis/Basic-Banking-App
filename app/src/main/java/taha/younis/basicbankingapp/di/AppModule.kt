package taha.younis.basicbankingapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import taha.younis.basicbankingapp.db.BankDAO
import taha.younis.basicbankingapp.db.BankDatabase
import taha.younis.basicbankingapp.repositoy.BankRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBankDao(bankDatabase: BankDatabase): BankDAO {
        return bankDatabase.getBankDao()
    }

    @Provides
    @Singleton
    fun provideBankDatabase(@ApplicationContext appContext: Context): BankDatabase {
        return Room.databaseBuilder(
            appContext,
            BankDatabase::class.java,
            "bank_db.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRepository(bankDAO: BankDAO): BankRepository{
        return BankRepository(bankDAO)
    }
}