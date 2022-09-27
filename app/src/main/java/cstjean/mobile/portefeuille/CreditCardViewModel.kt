package cstjean.mobile.portefeuille

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cstjean.mobile.portefeuille.creditcard.CreditCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class CreditCardViewModel(creditCardId: UUID) : ViewModel() {
    private val creditCardRepository = CreditCardRepository.get()

    private val _creditCard: MutableStateFlow<CreditCard?> = MutableStateFlow(null)
    val creditCard: StateFlow<CreditCard?>
        get() = _creditCard.asStateFlow()

    init {
        viewModelScope.launch {
            _creditCard.value = creditCardRepository.getCreditCard(creditCardId)
        }
    }

    fun updateCreditCard(onUpdate: (CreditCard) -> CreditCard) {
        _creditCard.update { oldCreditCard -> oldCreditCard?.let {onUpdate(it)} }
    }

    override fun onCleared() {
        super.onCleared()
        creditCard.value?.let { creditCardRepository.updateCreditCard(it) }
    }
}

class CreditCardViewModelFactory(private val creditCardId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreditCardViewModel(creditCardId) as T
    }
}