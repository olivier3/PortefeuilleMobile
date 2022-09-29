package cstjean.mobile.portefeuille.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cstjean.mobile.portefeuille.creditcard.CreditCard

/**
 * Classe de création de BD
 *
 * @author Olivier Bilodeau et Laura Tram
 */
@Database(entities = [CreditCard::class], version = 1, exportSchema = false)
abstract class CreditCardDatabase : RoomDatabase() {
    abstract fun creditCardDao(): CreditCardDao
}

