package com.example.farmersecom.features.cart.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmersecom.base.BaseFragment
import com.example.farmersecom.databinding.FragmentCartBinding
import com.example.farmersecom.features.cart.presentation.CartViewModel
import com.example.farmersecom.utils.constants.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>()
{

    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartItemAdapter: CartItemAdapter
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, root: Boolean): FragmentCartBinding
    {
        return  FragmentCartBinding.inflate(inflater,container,false)
    } // createView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler(binding.cartFragmentRecyclerView)
        subscribeToCartResponseFlow()
    } // onViewCreated closed


    private fun subscribeToCartResponseFlow()
    {

        viewModel.getAllCartItems.asLiveData().observe(viewLifecycleOwner)
        {
            cartItemAdapter.submitList(it)
            Timber.tag(TAG).d("${it.size}")
        } //

        viewModel.getSubtotal.asLiveData().observe(viewLifecycleOwner)
        {
            Timber.tag(TAG).d("${it}")
            binding.fragmentCartSubTotalTextView.text = it.toString()
        } //

        viewModel.getTotal.asLiveData().observe(viewLifecycleOwner)
        {
            Timber.tag(TAG).d("${it}")
            binding.fragmentCartTotalPriceTextView.text = it.toString()
        } //

        viewModel.getDeliveryCharges.asLiveData().observe(viewLifecycleOwner)
        {
            Timber.tag(TAG).d("${it}")
            binding.fragmentCartDeliveryChargesTextView.text = it.toString()
        } //

    } // CartFragment closed



    private fun setupRecycler(recycler: RecyclerView)
    {
        cartItemAdapter = CartItemAdapter()

        cartItemAdapter.onDeleteCartItemClicked()
        {
            viewModel.deleteCartItem(it)
        }

        cartItemAdapter.onQuantityChanged()
        { productId, quantity ->
            viewModel.changeQuantity(productId,quantity)
        }

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = cartItemAdapter
    } // setupHomeSlider closed



} // cardFragment closed