package com.planetnoobs.mandi.main.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.planetnoobs.mandi.main.models.ApiResponse
import com.planetnoobs.mandi.main.view.adapters.ProductRecyclerViewAdapter
import com.planetnoobs.mandi.main.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.*
import kotlinx.android.synthetic.main.bottom_sheet_dialog_layout.view.*
import planetnoobs.mandi.R
import planetnoobs.mandi.databinding.BottomSheetDialogLayoutBinding
import planetnoobs.mandi.databinding.FragmentProductlistBinding


class ProductListFragment : Fragment() {

    private lateinit var productListClone: MutableList<ApiResponse.Record>
    private var mainViewModel: MainViewModel? = null
    private lateinit var productFragmentBinding: FragmentProductlistBinding
    private lateinit var productRecyclerViewAdapter: ProductRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productFragmentBinding = FragmentProductlistBinding.inflate(inflater, container, false)

        return productFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        productRecyclerViewAdapter = ProductRecyclerViewAdapter()
        productFragmentBinding.productList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = productRecyclerViewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        productFragmentBinding.swiperefresh.setOnRefreshListener {
            mainViewModel?.mandiList?.removeObservers(this)
            setupObserver()
        }

        productFragmentBinding.filterButton.setOnClickListener {
            showBottomSheetDialog()
        }

        setupObserver()
    }


    private fun ChipGroup.addChip(context: Context, label: String) {
        Chip(context).apply {
            id = label.hashCode()
            text = label
            isClickable = true
            isCheckable = true
            isCheckedIconVisible = true
            isFocusable = true
            addView(this)
        }
    }


    private fun showBottomSheetDialog() {
        val binding = BottomSheetDialogLayoutBinding.inflate(layoutInflater).root
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding)
        bottomSheetDialog.show()

        bottomSheetDialog.setOnDismissListener {

        }

        val stateList = productListClone.map { it.state }.distinct()
        val districtList = productListClone.map { it.district }.distinct()

        stateList.forEach {
            binding.state_group.addChip(requireContext(), it)
        }

        districtList.forEach {
            binding.district_group.addChip(requireContext(), it)
        }

        binding.apply_filter.setOnClickListener {
            var productListFilter = productListClone
            val priceCheckedId = binding.price_group.checkedChipId
            val districtCheckedId = binding.district_group.checkedChipIds
            val stateCheckedId = binding.state_group.checkedChipIds
            val dateCheckedId = binding.date_group.checkedChipId

            if (priceCheckedId == R.id.price_chip_low) {
                productListFilter =
                    productListFilter.sortedBy { it.modalPrice.toInt() } as MutableList<ApiResponse.Record>
            }

            if (priceCheckedId == R.id.price_chip_high) {
                productListFilter = productListFilter.sortedBy { it.modalPrice.toInt() }
                    .reversed() as MutableList<ApiResponse.Record>
            }

            if (dateCheckedId == R.id.data_chip_new) {
                productListFilter =
                    productListFilter.sortedByDescending { it.arrivalDate } as MutableList<ApiResponse.Record>
            }

            if (dateCheckedId == R.id.data_chip_old) {
                productListFilter = productListFilter.sortedByDescending { it.arrivalDate }
                    .reversed() as MutableList<ApiResponse.Record>
            }

            if (stateCheckedId.isNotEmpty()) {
                productListFilter =
                    productListFilter.filter { stateCheckedId.contains(it.state.hashCode()) } as MutableList<ApiResponse.Record>
            }

            if (districtCheckedId.isNotEmpty()) {
                productListFilter =
                    productListFilter.filter { districtCheckedId.contains(it.district.hashCode()) } as MutableList<ApiResponse.Record>
            }

            productRecyclerViewAdapter.setProductList(productListFilter)

            val navController = findNavController(this);

            bottomSheetDialog.dismiss()
        }
        binding.reset_filter.setOnClickListener {
            binding.price_group.clearCheck()
            binding.district_group.clearCheck()
            binding.state_group.clearCheck()
            binding.date_group.clearCheck()

            mainViewModel?.mandiList?.removeObservers(this)
            setupObserver()

            bottomSheetDialog.dismiss()
        }
    }


    private fun setupObserver() {
        mainViewModel?.mandiList?.observeForever { list ->
            productRecyclerViewAdapter.setProductList(list.toMutableList())
            productListClone = list.toMutableList()
        }

        mainViewModel?.progressBarData?.observeForever {
            productFragmentBinding.swiperefresh.isRefreshing = it
        }

    }


    companion object {

    }
}