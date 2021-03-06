package com.pavneet_singh.mvvm_flow_coroutine_testing.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.pavneet_singh.mvvm_flow_coroutine_testing.ui.main.usecase.ChocolateAction
import com.pavneet_singh.mvvm_flow_coroutine_testing.ui.main.usecase.ChocolateResult
import com.pavneet_singh.mvvm_flow_coroutine_testing.ui.main.usecase.ChocolateUseCase
import com.pavneet_singh.mvvm_flow_coroutine_testing.ui.main.usecase.ChocolateViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

/**
 * Created by Pavneet_Singh on 20/03/20.
 */

class ChocolateViewModel(private val useCase: ChocolateUseCase) :
    ViewModel() {

    private val viewState =
        ChocolateViewState()

    /**
     *  Fetch the dummy data as Flow, from use case
     *  then return the LiveData after applying the transformation via mapping
     */
    fun onOptionsSelected(): LiveData<ChocolateViewState> {
        return useCase.getListOfChocolates(ChocolateAction.GetChocolateList)
            .map {
                when (it) {
                    is ChocolateResult.Loading -> viewState.copy(loading = true)
                    is ChocolateResult.ChocolateList -> viewState.copy(
                        loading = false,
                        data = it.choclateList
                    )
                    is ChocolateResult.Error -> viewState.copy(loading = false, error = "Error")
                    else -> viewState.copy(loading = false, error = "Error")
                }
            }.asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)
    }
}