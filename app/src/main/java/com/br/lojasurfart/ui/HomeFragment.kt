package com.br.lojasurfart.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.br.lojasurfart.R
import com.br.lojasurfart.model.Product
import com.br.lojasurfart.ui.adapters.RecyclerAdapterProduct
import com.br.lojasurfart.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), RecyclerAdapterProduct.OnProductListener {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val homeViewModel: HomeViewModel by viewModel()
    private var listProduct = listOf<Product>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterProduct: RecyclerAdapterProduct
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.mainViewStateLiveData.observe(viewLifecycleOwner, Observer {
            handlerState(it)
        })
    }

    private fun handlerState(state: HomeActivityState) {
        when (state) {
            is HomeActivityState.Loading -> showLoading()
            is HomeActivityState.Success -> {
                hideLoading()
                listProduct = state.list
                initRecyclerView(state.list)
            }
            is HomeActivityState.Error -> {
                hideLoading()
                showError()
            }
        }
    }

    private fun initRecyclerView(productList: List<Product>) {
        recyclerView = recycler_view

        // Use a Grid Layout Manager
        layoutManager = GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false)
        // layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager

        // mProgressBar.visibility = View.VISIBLE

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // Adapter
        adapterProduct = RecyclerAdapterProduct(requireContext(), productList, this)
        recyclerView.adapter = adapterProduct
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE

    }

    private fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    private fun showError() {
        txvErrorProduct.text = "Erro ao buscar produtos"
        txvErrorProduct.visibility = View.VISIBLE
    }

    override fun onProductClick(position: Int) {
        val product = listProduct[position]
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

}