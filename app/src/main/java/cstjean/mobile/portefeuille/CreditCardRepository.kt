package cstjean.mobile.portefeuille

import android.content.Context

class CreditCardRepository private constructor(context: Context) {

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