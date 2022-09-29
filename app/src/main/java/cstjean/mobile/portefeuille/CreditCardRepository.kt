package cstjean.mobile.portefeuille

import android.content.Context
import androidx.room.Room
import cstjean.mobile.portefeuille.creditcard.CreditCard
import cstjean.mobile.portefeuille.database.CreditCardDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "credit-card-database"

/**
 * Singleton pour la database.
 *
 * @author Olivier Bilodeau et Laura Tram
 */
class CreditCardRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val database: CreditCardDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CreditCardDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getCreditCards(): Flow<List<CreditCard>> = database.creditCardDao().getCreditCards()

    suspend fun getCreditCard(id: UUID): CreditCard = database.creditCardDao().getCreditCard(id)

    suspend fun addCreditCard(creditCard: CreditCard) {
        database.creditCardDao().addCreditCard(creditCard)
    }

    fun updateCreditCard(creditCard: CreditCard) {
        coroutineScope.launch {
            database.creditCardDao().updateCreditCard(creditCard)
        }
    }

    companion object {
        private var INSTANCE: CreditCardRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CreditCardRepository(context)
            }
        }

        fun get(): CreditCardRepository {
            return INSTANCE
                ?: throw IllegalStateException("CreditCardRepository doit être initialisé.")
        }
    }
}