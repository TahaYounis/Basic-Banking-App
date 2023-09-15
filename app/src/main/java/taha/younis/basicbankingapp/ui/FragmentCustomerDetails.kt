package taha.younis.basicbankingapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import taha.younis.basicbankingapp.R
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.databinding.FragmentCustomerDetailsBinding
import taha.younis.basicbankingapp.viewmodel.CustomersViewModel

@AndroidEntryPoint
class FragmentCustomerDetails : Fragment(R.layout.fragment_customer_details) {

    private lateinit var binding: FragmentCustomerDetailsBinding
    private val args by navArgs<FragmentCustomerDetailsArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = "Customer details"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        val customer = args.customer

        binding.apply {
            tvName.text = customer.name
            tvPhone.text = customer.phone
            tvEmail.text = customer.email
            tvAccountNo.text = customer.AccountNo
            tvBalance.text = "${customer.balance}"

            btnTransferMoney.setOnClickListener {
                if (tvBalance.text.equals("0"))
                    Toast.makeText(
                        requireContext(),
                        "${tvName.text} not have money",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    openDialog(requireContext())
            }
        }
    }

    private fun openDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Transfer money")
            setMessage("Do you want transfer money")
            val input = EditText(context)
            setView(input)

            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            setPositiveButton("Yes") { dialog, _ ->
                val enteredText = input.text.toString()

                val bundle = bundleOf(
                    "money" to enteredText.toInt(),
                    "customer" to args.customer
                )
                findNavController().navigate(
                    R.id.action_fragmentCustomerDetails_to_fragmentSeletctCustomer,
                    bundle
                )

                dialog.dismiss()
            }
        }
        alertDialog.create()
        alertDialog.show()
    }
}