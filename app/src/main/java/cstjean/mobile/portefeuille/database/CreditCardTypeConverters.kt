package cstjean.mobile.portefeuille.database

import androidx.room.TypeConverter
import java.util.*

class CreditCardTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}