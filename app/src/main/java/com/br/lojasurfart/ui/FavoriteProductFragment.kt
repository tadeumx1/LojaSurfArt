package com.br.lojasurfart.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.br.lojasurfart.R
import com.br.lojasurfart.viewmodels.FavoriteProductViewModel

class FavoriteProductFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteProductFragment()
    }

    private lateinit var viewModel: FavoriteProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_product_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoriteProductViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
