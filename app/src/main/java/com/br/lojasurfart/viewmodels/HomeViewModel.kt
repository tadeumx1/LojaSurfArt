package com.br.lojasurfart.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.lojasurfart.model.Product
import com.br.lojasurfart.repositories.ProductRepository
import com.br.lojasurfart.ui.HomeActivityState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val viewModelJob = SupervisorJob()
    private val viewModeScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val loading: MutableLiveData<Boolean> = MutableLiveData()
    private val error: MutableLiveData<Throwable> = MutableLiveData()

    private val mainViewStateMutableLiveData = MutableLiveData<HomeActivityState>()
    val mainViewStateLiveData: LiveData<HomeActivityState> = mainViewStateMutableLiveData

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModeScope.launch {
            loading.value = true
            mainViewStateMutableLiveData.postValue(HomeActivityState.Loading)

            try {
                val productResponse = productRepository.getProducts()
                val productListRepository: List<Product> = productResponse.docs
                val listProduct = mutableListOf<Product>()

                productListRepository.forEach {
                    listProduct.add(it)
                }

                mainViewStateMutableLiveData.postValue(HomeActivityState.Success(listProduct))
            } catch (t: Throwable) {
                error.value = t

                mainViewStateMutableLiveData.postValue(HomeActivityState.Error)

            } finally {
                loading.value = false
            }

        }
    }
}
