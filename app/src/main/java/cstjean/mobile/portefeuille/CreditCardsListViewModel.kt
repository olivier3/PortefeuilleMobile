package cstjean.mobile.portefeuille

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.portefeuille.creditcard.CreditCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class CreditCardsListViewModel : ViewModel() {
    private val creditCardRepository = CreditCardRepository.get()

    private val _creditCards: MutableStateFlow<List<CreditCard>> = MutableStateFlow(emptyList())
    val creditCards: StateFlow<List<CreditCard>>
        get() = _creditCards.asStateFlow()

    init {
        viewModelScope.launch {
            loadCreditCards()

            creditCardRepository.getCreditCards().collect {
                _creditCards.value = it
            }
        }
    }

    suspend fun loadCreditCards() {
        val creditCard = mutableListOf<CreditCard>()
        delay(5000)
        // Données de tests
        for (i in 0 until 10) {
            val creditCard = CreditCard(
                UUID.randomUUID(),
                "Travail #$i",
                5555,
                Date()
            )

            addCreditCard(creditCard)
        }
    }

    suspend fun addCreditCard(creditCard: CreditCard) {
        creditCardRepository.addCreditCard(creditCard)
    }
}