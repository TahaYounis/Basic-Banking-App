package taha.younis.basicbankingapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import taha.younis.basicbankingapp.data.Customer
import taha.younis.basicbankingapp.data.History
import taha.younis.basicbankingapp.databinding.ItemCustomerBinding
import taha.younis.basicbankingapp.databinding.ItemHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.VH>() {

    inner class VH(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.apply {
                tvSender.text = history.sender
                tvReceiver.text = history.receiver
                tvAmount.text = history.amount
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.transfer_id == newItem.transfer_id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val customer = differ.currentList[position]
        holder.bind(customer)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}