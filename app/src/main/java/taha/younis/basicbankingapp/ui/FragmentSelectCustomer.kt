package taha.younis.basicbankingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import taha.younis.basicbankingapp.R
import taha.younis.basicbankingapp.adapters.CustomerAdapter
import taha.younis.basicbankingapp.data.History
import taha.younis.basicbankingapp.databinding.FragmentSelectCustomerBinding
import taha.younis.basicbankingapp.utils.Resource
import taha.younis.basicbankingapp.viewmodel.CustomersViewModel
import taha.younis.basicbankingapp.viewmodel.HistoryViewModel

@AndroidEntryPoint
class FragmentSelectCustomer : Fragment(R.layout.fragment_select_customer) {

    private lateinit var binding: FragmentSelectCustomerBinding
    private lateinit var customerAdapter: CustomerAdapter
    private val viewModel by viewModels<CustomersViewModel>()
    private val viewModelHistory by viewModels<HistoryViewModel>()
    private val args by navArgs<FragmentSelectCustomerArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectCustomerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customer = args.customer
        val money = args.money

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Select Customer"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        setupCustomerAdapter()

        customerAdapter.onCLick = { receiver ->
            viewModel.updateSender(customer, money)
            viewModel.updateReceiver(receiver,money)
            viewModelHistory.insertToHistory(History(sender = customer.name, receiver = receiver.name, amount = "$money"))
            findNavController().navigate(R.id.action_fragmentSeletctCustomer_to_fragmentCustomers)
            findNavController().popBackStack(R.id.fragmentSeletctCustomer, true)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allCustomers.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE

                            val mutableList = it.data as MutableList
                            mutableList.removeIf {
                                it == customer
                            }
                            customerAdapter.differ.submitList(mutableList)
                        }
                        else -> Unit

                    }
                }
            }
        }
    }

    private fun setupCustomerAdapter() {
        customerAdapter = CustomerAdapter()
        binding.rvSelectCustomer.apply {
            adapter = customerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}