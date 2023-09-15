package taha.younis.basicbankingapp.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import taha.younis.basicbankingapp.R
import taha.younis.basicbankingapp.adapters.CustomerAdapter
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.databinding.FragmentCustomersBinding
import taha.younis.basicbankingapp.utils.Resource
import taha.younis.basicbankingapp.viewmodel.CustomersViewModel

@AndroidEntryPoint
class FragmentCustomers : Fragment(R.layout.fragment_customers) {

    private lateinit var binding: FragmentCustomersBinding
    private lateinit var customerAdapter: CustomerAdapter
    private val viewModel by viewModels<CustomersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "All Customers"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_histpry)
        actionBar?.setHomeActionContentDescription("open history fragment")

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        setHasOptionsMenu(true)

        setupCustomerAdapter()

        customerAdapter.onCLick = {
            val b = Bundle().apply { putParcelable("customer", it) }
            findNavController().navigate(
                R.id.action_fragmentCustomers_to_fragmentCustomerDetails, b
            )
        }


        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.allCustomers.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            customerAdapter.differ.submitList(it.data)
                        }
                        else -> Unit

                    }
                }
            }
        }
    }

    private fun setupCustomerAdapter() {
        customerAdapter = CustomerAdapter()
        binding.rvAllCustomers.apply {
            adapter = customerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.action_fragmentCustomers_to_fragmentHistory2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}