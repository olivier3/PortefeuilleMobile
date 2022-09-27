package cstjean.mobile.portefeuille

import android.content.Context
import androidx.room.Room
import cstjean.mobile.portefeuille.creditcard.CreditCard
import cstjean.mobile.portefeuille.database.CreditCardDatabase
import kotlinx.coroutines.flow.Flow
import java.util.UUID

private const val DATABASE_NAME = "credit-card-database"

class CreditCardRepository private constructor(context: Context) {
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

    companion object {
        private var INSTANCE: CreditCardRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CreditCardRepository(context)
            }
        }

        fun get(): CreditCardRepository {
            return INSTANCE ?:
            throw IllegalStateException("CreditCardRepository doit être initialisé.")
        }
    }
}