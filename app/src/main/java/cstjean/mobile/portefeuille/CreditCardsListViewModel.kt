package cstjean.mobile.portefeuille

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.portefeuille.creditcard.CreditCard
import io.github.serpro69.kfaker.Faker
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
        val faker = Faker()
        // Données de tests
        for (i in 0 until 1) {
            val creditCard = CreditCard(
                UUID.randomUUID(),
                faker.name.name(),
                7868,
                Date()
            )
            addCreditCard(creditCard)
        }
    }

    suspend fun addCreditCard(creditCard: CreditCard) {
        creditCardRepository.addCreditCard(creditCard)
    }
}