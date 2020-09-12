package com.br.lojasurfart.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.lojasurfart.R
import com.br.lojasurfart.ui.adapters.RecyclerAdapterCategory
import com.br.lojasurfart.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.category_fragment.*
import kotlinx.android.synthetic.main.home_fragment.progress_bar
import kotlinx.android.synthetic.main.home_fragment.recycler_view
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryFragment : Fragment(), RecyclerAdapterCategory.OnCategoryListener {

    companion object {
        fun newInstance() = CategoryFragment()
    }

    private val categoryViewModel: CategoryViewModel by viewModel()
    private var listCategory = listOf<String>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterCategory: RecyclerAdapterCategory
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        categoryViewModel.mainViewStateLiveData.observe(viewLifecycleOwner, Observer {
            handlerState(it)
        })
    }

    private fun handlerState(state: CategoryState) {
        when (state) {
            is CategoryState.Loading -> showLoading()
            is CategoryState.Success -> {
                hideLoading()
                listCategory = state.list
                initRecyclerView(state.list)
            }
            is CategoryState.Error -> {
                hideLoading()
                showError()
            }
        }
    }

    private fun initRecyclerView(categoryList: List<String>) {
        recyclerView = recycler_view

        // Use a Grid Layout Manager
        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        // layoutManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = layoutManager

        // mProgressBar.visibility = View.VISIBLE

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        // Adapter
        adapterCategory = RecyclerAdapterCategory(requireContext(), categoryList, this)
        recyclerView.adapter = adapterCategory
    }

    private fun showLoading() {
        progress_bar.visibility = View.VISIBLE

    }

    private fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    private fun showError() {
        txvErrorCategory.text = "Erro ao buscar categorias"
        txvErrorCategory.visibility = View.VISIBLE
    }

    override fun onCategoryClick(position: Int) {
        val category = listCategory[position]
        Toast.makeText(activity, "Category Selected $category", Toast.LENGTH_LONG).show()
    }

}
