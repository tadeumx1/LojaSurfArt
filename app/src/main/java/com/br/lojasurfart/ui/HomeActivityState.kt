package com.br.lojasurfart.ui

import com.br.lojasurfart.model.Product

sealed class HomeActivityState {
    class Success(val list: List<Product>) : HomeActivityState()
    object Loading : HomeActivityState()
    object Error : HomeActivityState()
}