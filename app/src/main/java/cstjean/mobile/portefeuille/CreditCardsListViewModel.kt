package cstjean.mobile.portefeuille

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.portefeuille.creditcard.CreditCard
import io.github.serpro69.kfaker.Faker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel pour la liste des cartes de crédit.
 *
 * @author Olivier Bilodeau et Laura Tram
 */
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
        for (i in 0 until 6) {

            val creditCard = createCreditCard(faker)
            addCreditCard(creditCard)
        }
    }

    fun createCreditCard(faker: Faker): CreditCard {

        val regex = "^.([45])[0-9]{3}-.*".toRegex()
        val randomValue = (0..1).random()
        val cardType = if (randomValue == 0) "visa" else "mastercard"
        var type: String
        val month = (1..12).random().toString()
        val year = (2000..2050).random().toString()

        while (true) {
            type = faker.finance.creditCard(cardType)
            if (regex.containsMatchIn(type)) {
                type = type.drop(1).dropLast(1)
                break
            }
        }

        val creditCard = CreditCard(
            UUID.randomUUID(),
            faker.name.name(),
            type,
            "$month/$year"
        )
        return creditCard
    }

    suspend fun addCreditCard(creditCard: CreditCard) {
        creditCardRepository.addCreditCard(creditCard)
    }
}