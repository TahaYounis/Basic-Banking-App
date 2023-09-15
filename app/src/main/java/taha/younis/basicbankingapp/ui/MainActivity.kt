package taha.younis.basicbankingapp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import taha.younis.basicbankingapp.R
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.databinding.ActivityMainBinding
import taha.younis.basicbankingapp.viewmodel.CustomersViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<CustomersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView3) as NavHostFragment

        viewModel.insertCustomer(Customer("01092242483", "Taha", "pro.tahayounis@gmail.com", "3131", 10000))
        viewModel.insertCustomer(Customer("0105854585", "Mohamed", "mohamed123@gmail.com", "2112", 5000))
        viewModel.insertCustomer(Customer("0127854585", "Younis", "younis_123@gmail.com", "1508", 3000))
        viewModel.insertCustomer(Customer("0115454585", "Ahmed", "ahmed_1122@gmail.com", "8704", 4000))
        viewModel.insertCustomer(Customer("0120754585", "Nada", "nada878@gmail.com", "5698", 2000))
        viewModel.insertCustomer(Customer("0112344585", "Omar", "omar_122@gmail.com", "4785", 0))
        viewModel.insertCustomer(Customer("0100244585", "Khaled", "khaled878@gmail.com", "1545", 0))
        viewModel.insertCustomer(Customer("0118774585", "Abdo", "abdo_878@gmail.com", "5644", 0))
        viewModel.insertCustomer(Customer("0125954585", "Rode", "rode_887@gmail.com", "4578", 0))
        viewModel.insertCustomer(Customer("0115574585", "Yassin", "yassin_001@gmail.com", "8475", 0))
        viewModel.insertCustomer(Customer("0125574585", "Akram", "akram788@gmail.com", "4578", 0))
        viewModel.insertCustomer(Customer("0105674585", "Mahmoud", "mahmoud044@gmail.com", "0407", 0))

    }
}