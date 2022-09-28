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
            // loadCreditCards()

            creditCardRepository.getCreditCards().collect {
                _creditCards.value = it
            }
        }
    }

    suspend fun loadCreditCards() {
        val faker = Faker()
        val regex = "^.(4|5)[0-9]{3}-.*".toRegex()

        // Données de tests
        for (i in 0 until 6) {
            val randomValue = (0..1).random()

            val cardType = if (randomValue == 0) "visa" else "mastercard"

            var type: String

            while (true) {
                 type = faker.finance.creditCard(cardType)
                if (regex.containsMatchIn(type)) {
                    type = type.drop(1).dropLast(1)
                    break
                }
            }

            val month = (1..12).random().toString()
            val year = (2000..2050).random().toString()

            val creditCard = CreditCard(
                UUID.randomUUID(),
                faker.name.name(),
                type,
                "$month/$year"
            )
            addCreditCard(creditCard)
        }
    }

    suspend fun addCreditCard(creditCard: CreditCard) {
        creditCardRepository.addCreditCard(creditCard)
    }
}