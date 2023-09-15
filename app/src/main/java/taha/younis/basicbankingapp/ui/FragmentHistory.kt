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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import taha.younis.basicbankingapp.R
import taha.younis.basicbankingapp.adapters.CustomerAdapter
import taha.younis.basicbankingapp.adapters.HistoryAdapter
import taha.younis.basicbankingapp.databinding.FragmentHistoryBinding
import taha.younis.basicbankingapp.utils.Resource
import taha.younis.basicbankingapp.viewmodel.HistoryViewModel

@AndroidEntryPoint
class FragmentHistory: Fragment(R.layout.fragment_history) {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Transfer history"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        setupCustomerAdapter()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.history.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            historyAdapter.differ.submitList(it.data)
                        }
                        else -> Unit

                    }
                }
            }
        }
    }

    private fun setupCustomerAdapter() {
        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            adapter = historyAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}