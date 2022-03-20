package com.planetnoobs.mandi.main.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.planetnoobs.mandi.main.models.ApiResponse
import planetnoobs.mandi.databinding.FragmentProductListItemBinding


class ProductRecyclerViewAdapter(
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>(), Filterable {
    private var productListFiltered: MutableList<ApiResponse.Record> = ArrayList()

    private val differCallback = object : DiffUtil.ItemCallback<ApiResponse.Record>() {

        override fun areItemsTheSame(
            oldItem: ApiResponse.Record,
            newItem: ApiResponse.Record
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ApiResponse.Record,
            newItem: ApiResponse.Record
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)


    fun setProductList(productList: MutableList<ApiResponse.Record>) {
        differ.submitList(productList)
        this.productListFiltered = differ.currentList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.market.text = item.market
        holder.modalPrice.text = "₹"+item.modalPrice
        holder.productName.text = item.commodity
        holder.date.text = item.arrivalDate
        holder.state.text = item.state + " - " + item.district
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(binding: FragmentProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val market: TextView = binding.market
        val date: TextView = binding.dateAdded
        val modalPrice: TextView = binding.modalPrice
        val productName: TextView = binding.productName
        val state: TextView = binding.state

    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                productListFiltered = if (charString.isEmpty()) differ.currentList else {
                    val filteredList = ArrayList<ApiResponse.Record>()
                    differ.currentList
                        .filter {
                            (it.commodity.contains(constraint!!)) or
                                    (it.commodity.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = productListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                productListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as MutableList<ApiResponse.Record>
                notifyDataSetChanged()
            }
        }

    }
}