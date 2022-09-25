package cstjean.mobile.portefeuille.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cstjean.mobile.portefeuille.creditcard.CreditCard

class CreditCardDatabase {
    @Database(entities = [CreditCard::class], version = 1)
    @TypeConverters(CreditCardTypeConverters::class)
    abstract class CreditCardDatabase : RoomDatabase() {
        abstract fun creditCardDao(): CreditCardDao
    }
}