package com.br.lojasurfart.ui

sealed class CategoryState {
    class Success(val list: List<String>) : CategoryState()
    object Loading : CategoryState()
    object Error : CategoryState()
}