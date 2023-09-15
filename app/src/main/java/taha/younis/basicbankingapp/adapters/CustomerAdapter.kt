package taha.younis.basicbankingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.databinding.ItemCustomerBinding

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.VH>() {

    inner class VH(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer) {
            binding.apply {
                tvCustomerName.text = customer.name
                tvCustomerPhone.text = customer.phone
                tvCustomerBalance.text = "${customer.balance}"
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.phone == newItem.phone
        }

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemCustomerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val customer = differ.currentList[position]
        holder.bind(customer)

        holder.itemView.setOnClickListener {
            onCLick?.invoke(customer)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onCLick: ((Customer) -> Unit)? = null
}