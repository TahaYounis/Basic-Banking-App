package taha.younis.basicbankingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import taha.younis.basicbankingapp.R
import taha.younis.basicbankingapp.databinding.FragmentHomeBinding

@AndroidEntryPoint
class FragmentHome : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Bank System"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        binding.btnViewCustomers.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentHome_to_fragmentCustomers4)
        }
    }
}