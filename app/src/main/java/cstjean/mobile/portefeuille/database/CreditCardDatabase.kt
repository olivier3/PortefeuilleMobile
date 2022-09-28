package cstjean.mobile.portefeuille.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cstjean.mobile.portefeuille.creditcard.CreditCard


@Database(entities = [CreditCard::class], version = 1, exportSchema = false)
@TypeConverters(CreditCardTypeConverters::class)
abstract class CreditCardDatabase : RoomDatabase() {
    abstract fun creditCardDao(): CreditCardDao
}

