package com.br.lojasurfart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.lojasurfart.repositories.ProductRepository
import com.br.lojasurfart.ui.CategoryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CategoryViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModeScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<Throwable> = MutableLiveData()

    private val mainViewStateMutableLiveData = MutableLiveData<CategoryState>()
    val mainViewStateLiveData: LiveData<CategoryState> = mainViewStateMutableLiveData

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModeScope.launch {
            loading.value = true
            mainViewStateMutableLiveData.postValue(CategoryState.Loading)

            try {
                // val productResponse = productRepository.getCategories()
                // val productListRepository: List<Product> = productResponse.docs
                val categoryListRepository: List<String> = listOf(
                    "Novidades",
                    "Feminino",
                    "Masculino",
                    "Infantil",
                    "Moda Íntima",
                    "Calçados",
                    "Acessórios e Relógios",
                    "Beleza e Perfume",
                    "Outlet",
                    "Camisetas selecionadas"
                )
                // val listProduct = mutableListOf<Product>()

                // productListRepository.forEach {
                //     listProduct.add(it)
                // }

                mainViewStateMutableLiveData.postValue(CategoryState.Success(categoryListRepository))
            } catch (t: Throwable) {
                error.value = t

                mainViewStateMutableLiveData.postValue(CategoryState.Error)

            } finally {
                loading.value = false
            }

        }
    }
}
